package cz.airbank.cucumber.reports.api.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.airbank.cucumber.reports.api.converter.BuildRunMetadata2DaoBuildRunMetadataConverter;
import cz.airbank.cucumber.reports.api.converter.Feature2FeatureRunConverter;
import cz.airbank.cucumber.reports.api.converter.TestSuiteMetadata2DaoTestSuiteMetadataConverter;
import cz.airbank.cucumber.reports.api.exception.PersistException;
import cz.airbank.cucumber.reports.api.validation.FeatureValidator;
import cz.airbank.cucumber.reports.dao.BuildRunDao;
import cz.airbank.cucumber.reports.dao.DaoException;
import cz.airbank.cucumber.reports.dao.DataCollectDao;
import cz.airbank.cucumber.reports.dao.EmbeddingDao;
import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.entity.DaoBuildRunMetadata;
import cz.airbank.cucumber.reports.dao.entity.FeatureRun;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.entity.DaoTestSuiteMetadata;
import cz.airbank.cucumber.reports.transport.model.Embedding;
import cz.airbank.cucumber.reports.transport.model.Feature;
import cz.airbank.cucumber.reports.transport.model.FeatureMetadata;
import cz.airbank.cucumber.reports.transport.model.ScenarioDefinition;
import cz.airbank.cucumber.reports.transport.model.ScenarioRun;
import cz.airbank.cucumber.reports.transport.model.StepResult;

/**
 * Rest controller for collecting data from Cucumber reports run on Jenkins.
 *
 * @author Vaclav Stengl
 */
@RestController
public class DataCollectController {

    private static final Logger sLog = LoggerFactory.getLogger(DataCollectController.class);

    private final Feature2FeatureRunConverter feature2FeatureRunConverter;
    private final TestSuiteMetadata2DaoTestSuiteMetadataConverter testSuiteMetadataConverter;
    private final BuildRunMetadata2DaoBuildRunMetadataConverter buildRunMetadataConverter;

    private final DataCollectDao dataCollectDao;
    private final BuildRunDao buildRunDao;
    private final EmbeddingDao embeddingDao;

    private final FeatureValidator featureValidator;

    @Autowired
    public DataCollectController(Feature2FeatureRunConverter feature2FeatureRunConverter,
                                 TestSuiteMetadata2DaoTestSuiteMetadataConverter testSuiteMetadataConverter,
                                 BuildRunMetadata2DaoBuildRunMetadataConverter buildRunMetadataConverter,
                                 DataCollectDao dataCollectDao, BuildRunDao buildRunDao,
                                 EmbeddingDao embeddingDao,
                                 FeatureValidator featureValidator) {
        this.feature2FeatureRunConverter = feature2FeatureRunConverter;
        this.testSuiteMetadataConverter = testSuiteMetadataConverter;
        this.buildRunMetadataConverter = buildRunMetadataConverter;
        this.dataCollectDao = dataCollectDao;
        this.buildRunDao = buildRunDao;
        this.embeddingDao = embeddingDao;
        this.featureValidator = featureValidator;
    }

    /**
     * Receive data and store them.
     *
     * @param feature to store
     * @return {@link HttpStatus#OK} status for successful data collection and
     * {@link HttpStatus#PRECONDITION_FAILED} when fail occurs
     */
    @RequestMapping(path = "/rest/collect", method = RequestMethod.POST)
    public ResponseEntity collectData(@RequestBody Feature feature) {
        Assert.notNull(feature);
        //TODO is there way to register validator?
        featureValidator.validateFeature(feature);

        DaoBuildRunMetadata buildRunMetadata = buildRunMetadataConverter.convert(feature.getBuildRunMetadata());
        Assert.notNull(buildRunMetadata);

        try {
            BuildRun buildRun = buildRunDao.findByBuildNameAndSequentialNumber(buildRunMetadata.getProjectName(), buildRunMetadata.getSequentialNumber());
            persistEmbeddings(feature);

            FeatureRun featureRun = feature2FeatureRunConverter.convert(feature);

            if (buildRun == null) {
                buildRun = new BuildRun();
                buildRun.setMetadata(buildRunMetadata);
                buildRun.getMetadata().setPassed(true);

                sLog.debug("Creating build ({}-{})", buildRunMetadata.getProjectName(), buildRunMetadata.getSequentialNumber());
            }

            DaoTestSuiteMetadata daoTestSuiteMetadata = testSuiteMetadataConverter.convert(feature.getTestSuiteMetadata());
            Assert.notNull(daoTestSuiteMetadata);
            TestSuiteExecution execution = findTestSuiteExecution(buildRun, daoTestSuiteMetadata);

            if (execution == null) {
                execution = new TestSuiteExecution();
                execution.setMetadata(daoTestSuiteMetadata);
                execution.getMetadata().setPassed(true);
                buildRun.getTestSuites().add(execution);

                sLog.debug("Creating test suite execution ({})", execution.getMetadata());
            }

            execution.getFeatureRuns().add(featureRun);
            featureRun.setId(execution.getFeatureRuns().size() - 1);
            daoTestSuiteMetadata.setPassed(daoTestSuiteMetadata.isPassed() && featureRun.getMetadata().passed());
            buildRunMetadata.setPassed(buildRunMetadata.isPassed() && daoTestSuiteMetadata.isPassed());

            dataCollectDao.persist(execution);
            dataCollectDao.persist(buildRun);

            sLog.debug("Adding feature ({}) to ({}-{})", computeFeatureIdWithinBuild(feature),
                       buildRunMetadata.getProjectName(), buildRunMetadata.getSequentialNumber());
        } catch (DaoException e) {
            String msg = MessageFormat.format(
                "DB failure for feature {0}, build {1}-{2}, {3} reason: ", computeFeatureIdWithinBuild(feature),
                buildRunMetadata.getProjectName(), buildRunMetadata.getSequentialNumber(), buildRunMetadata.getBuildAt());
            sLog.debug(msg);
            throw new PersistException(msg, e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Persist embeddings inside posted feature.
     *
     * @param feature the data source
     */
    private void persistEmbeddings(Feature feature) {
        for (ScenarioDefinition scenarioDefinition : feature.getScenarioDefinitionList()) {
            persistInScenarioDefinition(scenarioDefinition);
        }

        if (feature.getBackground() != null) {
            persistInScenarioDefinition(feature.getBackground());
        }
    }

    /**
     * Find and persist embedding inside all scenario runs in one scenario definition.
     *
     * @param definition to search embeddings within
     */
    private void persistInScenarioDefinition(ScenarioDefinition definition) {
        for (ScenarioRun scenarioRun : definition.getScenarioRunList()) {
            storeEmbedding(scenarioRun.getBackgroundStepResults());
            storeEmbedding(scenarioRun.getScenarioStepResults());
        }
    }

    /**
     * Store embeddings inside step results.
     *
     * @param stepResults to process and save embeddings if there are any
     */
    private void storeEmbedding(List<StepResult> stepResults) {
        for (StepResult stepResult : stepResults) {
            for (Embedding embedding : stepResult.getEmbeddingList()) {
                InputStream stream = new ByteArrayInputStream(embedding.getContent());
                String id = embeddingDao.store(stream, embedding.getContentType());

                embedding.setId(id);
            }
        }
    }

    /**
     * Try to find test suite execution with equal metadata.
     *
     * @return test suite execution instance when metadata eq,
     *         null otherwise.
     */
    private TestSuiteExecution findTestSuiteExecution(BuildRun run, DaoTestSuiteMetadata daoTestSuiteMetadata) {
        for (TestSuiteExecution testSuiteExecution : run.getTestSuites()) {
            if (testSuiteExecution.getMetadata().equals(daoTestSuiteMetadata)) {
                return testSuiteExecution;
            }
        }

        return null;
    }

    /**
     * Unique feature id within build run
     */
    private String computeFeatureIdWithinBuild(Feature feature) {
        FeatureMetadata metadata = feature.getFeatureMetadata();
        StringBuilder idBuilder = new StringBuilder();

        if (!StringUtils.isEmpty(metadata.getModule())) {
            idBuilder.append(metadata.getModule());
            idBuilder.append('/');
        }
        idBuilder.append(metadata.getFilename());
        idBuilder.append(':');
        idBuilder.append(metadata.getGlue());

        return idBuilder.toString();
    }
}
