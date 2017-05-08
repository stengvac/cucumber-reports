package cz.airbank.cucumber.reports.view.reports.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cz.airbank.cucumber.reports.dao.BuildRunDao;
import cz.airbank.cucumber.reports.dao.TestSuiteExecutionDao;
import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.entity.DaoFeatureMetadata;
import cz.airbank.cucumber.reports.dao.entity.DaoBuildRunMetadata;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.entity.FeatureRun;
import cz.airbank.cucumber.reports.dao.to.BuildRunMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.BuildRunWithTestSuitesTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteReportTo;
import cz.airbank.cucumber.reports.view.model.BuildDetailWithCounts;
import cz.airbank.cucumber.reports.view.model.ModuleBuildCounts;
import cz.airbank.cucumber.reports.view.model.ProjectWithRuns;
import cz.airbank.cucumber.reports.view.reports.converter.BuildRun2BuildRunMetadataWithIdConverter;
import cz.airbank.cucumber.reports.view.reports.converter.BuildRunReportMapper;
import cz.airbank.cucumber.reports.view.reports.converter.DaoBuildRunMetadata2BuildRunMetadataConverter;
import cz.airbank.cucumber.reports.view.reports.converter.FeatureRun2FeatureMetadataWithIdConverter;
import cz.airbank.cucumber.reports.view.reports.converter.ProjectWithRunsTo2ProjectWithRunsConverter;
import cz.airbank.cucumber.reports.view.reports.converter.TestSuiteReportTo2TestSuiteReportConverter;
import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadataWithId;
import cz.airbank.cucumber.reports.view.reports.service.model.BuildRunReport;
import cz.airbank.cucumber.reports.view.reports.service.model.TestSuiteReport;

/**
 * Implementation for {@link ReportsService}.
 *
 * @author David Passler
 */
@Service
public class ReportsServiceImpl implements ReportsService {

    private final BuildRunDao buildRunDao;
    private final TestSuiteExecutionDao testSuiteExecutionDao;

    private final TestSuiteReportTo2TestSuiteReportConverter testSuiteReportConverter;
    private final FeatureRun2FeatureMetadataWithIdConverter featureWithResultConverter;
    private final BuildRun2BuildRunMetadataWithIdConverter buildPresentationConverter;
    private final DaoBuildRunMetadata2BuildRunMetadataConverter buildRunMetadataConverter;
    private final BuildRunReportMapper buildRunReportMapper;
    private final ProjectWithRunsTo2ProjectWithRunsConverter projectWithRunsConverter;

    @Autowired
    public ReportsServiceImpl(BuildRunDao buildRunDao,
                              TestSuiteExecutionDao testSuiteExecutionDao,
                              TestSuiteReportTo2TestSuiteReportConverter testSuiteReportConverter,
                              FeatureRun2FeatureMetadataWithIdConverter featureWithResultConverter,
                              BuildRun2BuildRunMetadataWithIdConverter buildPresentationConverter,
                              DaoBuildRunMetadata2BuildRunMetadataConverter buildRunMetadataConverter,
                              BuildRunReportMapper buildRunReportMapper,
                              ProjectWithRunsTo2ProjectWithRunsConverter projectWithRunsConverter) {
        this.buildRunDao = buildRunDao;
        this.testSuiteExecutionDao = testSuiteExecutionDao;
        this.testSuiteReportConverter = testSuiteReportConverter;
        this.featureWithResultConverter = featureWithResultConverter;
        this.buildPresentationConverter = buildPresentationConverter;
        this.buildRunMetadataConverter = buildRunMetadataConverter;
        this.buildRunReportMapper = buildRunReportMapper;
        this.projectWithRunsConverter = projectWithRunsConverter;
    }

    @Override
    public BuildDetailWithCounts getLatestBuildDetail(String buildName) {
        final BuildRun latestBuild = buildRunDao.findLatestBuildByName(buildName);

        if (latestBuild == null) {
            throw new IllegalStateException("No build with name '" + buildName + "' was found.");
        }

        return createBuildDetailWithCounts(latestBuild);
    }

    @Override
    public BuildDetailWithCounts getBuildDetail(String buildName, long buildNumber) {
        final BuildRun build = buildRunDao.findByBuildNameAndSequentialNumber(buildName, buildNumber);

        if (build == null) {
            throw new IllegalStateException("Build '" + buildName + "'-'" + buildNumber + "' not found.");
        }

        return createBuildDetailWithCounts(build);
    }

    @Override
    public List<ModuleBuildCounts> getModuleBuildCounts() {
        final List<BuildRun> allModulesWithLatestBuild = buildRunDao.getAllModulesWithLatestBuild();
        final Map<String, ModuleBuildCounts> moduleBuildCountsMap = new TreeMap<>();

        for (BuildRun buildRun : allModulesWithLatestBuild) {
            computeCountsForBuild(buildRun, moduleBuildCountsMap);
        }

        return new ArrayList<>(moduleBuildCountsMap.values());
    }

    @Override
    public BuildRunMetadataWithId getBuildRunPresentation(String buildName, long sequentialNumber) {
        BuildRun buildRun = buildRunDao.findByBuildNameAndSequentialNumber(buildName, sequentialNumber);

        return buildPresentationConverter.convert(buildRun);
    }

    @Override
    public List<BuildRunMetadataWithId> getBuildRunPresentations(String buildName, int maxRecords) {
        return buildRunDao.findBuildPresentationByBuildName(buildName, maxRecords)
            .stream().map(buildPresentationConverter::convert).collect(Collectors.toList());
    }

    @Override
    public BuildRunReport findBuildRunReportById(String buildId) {
        BuildRunWithTestSuitesTo buildRunWithTestSuitesTo = buildRunDao.findBuildRunReport(buildId);

        Assert.notNull(buildRunWithTestSuitesTo, "Build report for id (" + buildId + ") not found");

        DaoBuildRunMetadata metadata = buildRunWithTestSuitesTo.getBuildRunMetadataWithIdTo().getMetadata();
        BuildRunMetadataWithIdTo prev = buildRunDao.findPreviousBuildId(metadata.getProjectName(),
                                                                        metadata.getSequentialNumber());
        BuildRunMetadataWithIdTo next = buildRunDao.findNextBuildId(metadata.getProjectName(),
                                                                    metadata.getSequentialNumber());

        return buildRunReportMapper.convert(buildRunWithTestSuitesTo, prev, next);
    }

    @Override
    public TestSuiteReport findTestSuiteReport(String buildId, String testSuiteId) {
        TestSuiteReportTo testSuiteReportTo = testSuiteExecutionDao.findTestSuiteReport(testSuiteId);
        BuildRunMetadataWithIdTo buildRunMetadataWithIdTo = buildRunDao.findBuildRunMetadataWithIdById(buildId);

        return testSuiteReportConverter.convert(testSuiteReportTo, buildRunMetadataWithIdTo);
    }

    @Override
    public List<ProjectWithRuns> getLatestBuilds(int buildRunsPerProject) {
        return buildRunDao.getLatestBuildsPerProject(buildRunsPerProject).stream().map(
            projectWithRunsConverter::convert
        ).collect(Collectors.toList());
    }

    /**
     * Computes the counts for given build and saves them to the {@code resultCounts} map.
     *
     * @param build the build to be counted
     * @param resultCounts the map with results
     */
    private void computeCountsForBuild(BuildRun build, Map<String, ModuleBuildCounts> resultCounts) {
        for (TestSuiteExecution testSuiteExecution : build.getTestSuites()) {
            for (FeatureRun featureRun : testSuiteExecution.getFeatureRuns()) {
                DaoFeatureMetadata metadata = featureRun.getMetadata();

                if (!resultCounts.containsKey(metadata.getModule())) {
                    ModuleBuildCounts moduleBuildCounts = new ModuleBuildCounts();
                    moduleBuildCounts.setModuleName(metadata.getModule());

                    resultCounts.put(metadata.getModule(), moduleBuildCounts);
                }

                final ModuleBuildCounts moduleBuildCounts = resultCounts.get(metadata.getModule());
                moduleBuildCounts.setBuildRunMetadata(buildRunMetadataConverter.convert(build.getMetadata()));

                moduleBuildCounts.getFeatureWithResults().add(featureWithResultConverter.convert(featureRun));
                moduleBuildCounts.increaseFeatureCount();
                moduleBuildCounts.increaseScenarioCountBy(metadata.getScenarioDefinitionsTotalCount());
                moduleBuildCounts.increaseStepCountBy(metadata.getScenarioStepDefinitionsTotalCount());
                moduleBuildCounts.increaseStepCountBy(metadata.getBackgroundStepDefinitionsTotalCount());
            }
        }
    }

    /**
     * Create statistics model instance from build run with all necessary information.
     *
     * @param build to obtained data from
     * @return new instance of object with extracted information
     */
    private BuildDetailWithCounts createBuildDetailWithCounts(BuildRun build) {

        final Map<String, ModuleBuildCounts> moduleBuildCountsMap = new HashMap<>();
        computeCountsForBuild(build, moduleBuildCountsMap);
        final List<ModuleBuildCounts> counts = new ArrayList<>(moduleBuildCountsMap.values());

        counts.sort(Comparator.comparing(ModuleBuildCounts::getModuleName));

        final BuildDetailWithCounts buildDetailWithCounts = new BuildDetailWithCounts();
        buildDetailWithCounts.setBuildRunMetadata(buildRunMetadataConverter.convert(build.getMetadata()));
        buildDetailWithCounts.setCounts(counts);

        return buildDetailWithCounts;
    }
}