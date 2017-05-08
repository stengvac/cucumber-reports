package cz.airbank.cucumber.reports.view.reports.bean;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import cz.airbank.cucumber.reports.view.admin.service.ConfigService;
import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadata;
import cz.airbank.cucumber.reports.view.reports.model.feature.HookDefinitionReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.ResultReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadata;
import cz.airbank.cucumber.reports.view.reports.model.feature.ScenarioRunReport;
import cz.airbank.cucumber.reports.view.reports.service.FeatureReportService;
import cz.airbank.cucumber.reports.view.service.JSFUtilsService;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureReport;

/**
 * Backing bean for feature report page
 *
 * @author Vaclav Stengl
 */
@Named
@Scope(org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST)
@Component
public class FeatureReportBean {

    /**
     * Name of URL parameter with build id. Used to retrieve build run, which host this feature report.
     */
    public static final String BUILD_ID_PARAM_NAME = "buildId";

    /**
     * Name of URL parameter with suite id. Used to retrieve test suite, which contains this feature report.
     */
    public static final String TEST_SUITE_ID_PARAM_NAME = "suiteId";

    /**
     * Name of URL parameter with feature id.
     */
    public static final String FEATURE_ID_PARAM_NAME = "featureId";

    /**
     * Outcome used in {@code faces-config.xml} to redirect to feature report.
     */
    public static final String FEATURE_REPORT_OUTCOME = "toFeatureReport";

    private final FeatureReportService featureReportService;
    private final JSFUtilsService jsfUtilsService;
    private final ConfigService configService;

    private FeatureReport featureReport;
    private boolean imagesIncluded;

    @Autowired
    public FeatureReportBean(FeatureReportService featureReportService, JSFUtilsService jsfUtilsService,
                             ConfigService configService) {
        this.featureReportService = featureReportService;
        this.jsfUtilsService = jsfUtilsService;
        this.configService = configService;
    }

    @PostConstruct
    public void init() {
        String buildId = jsfUtilsService.getPageParameter(BUILD_ID_PARAM_NAME);
        String testSuiteId = jsfUtilsService.getPageParameter(TEST_SUITE_ID_PARAM_NAME);
        int featureId = Integer.valueOf(jsfUtilsService.getPageParameter(FEATURE_ID_PARAM_NAME));

        Assert.notNull(buildId);
        Assert.notNull(testSuiteId);

        featureReport = featureReportService.getFeatureReportData(buildId, testSuiteId, featureId);

        Assert.notNull(featureReport, MessageFormat.format(
                "Feature run with index '{0}' not found "
                        + "in build run  with id '{1}' and"
                        + "test suite with id '{2}'.", featureId, buildId, testSuiteId));
    }

    /**
     * Feature to report
     */
    public FeatureReport getFeatureReport() {
        return featureReport;
    }

    /**
     * Shortcut to feature metadata attribute.
     */
    public FeatureMetadata getFeatureMetadata() {
        return featureReport.getFeatureMetadataWithId().getMetadata();
    }

    /**
     * Shortcut to build run metadata attribute.
     */
    public BuildRunMetadata getBuildRunMetadata() {
        return featureReport.getBuildRunMetadataWithId().getMetadata();
    }

    /**
     * Create id for scenario run
     */
    public String getScenarioRunId(int scenarioRun) {
        return "scenarioRun" + scenarioRun;
    }

    /**
     * Images are included in html creation.
     */
    public boolean isImagesIncluded() {
        return imagesIncluded;
    }

    public void setImagesIncluded(boolean imagesIncluded) {
        this.imagesIncluded = imagesIncluded;
    }

    /**
     * Create full path to hook method.
     *
     * @param hookReport to create full location for
     * @return {@link cz.airbank.cucumber.reports.dao.entity.HookDefinition#getLocation()}
     * if {@link cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadata#getGlue()} is empty.
     * otherwise join return glue + DOT_SEPARATOR + location
     */
    public String createFullHookLocation(ResultReport<HookDefinitionReport> hookReport) {
        String glue = featureReport.getFeatureMetadataWithId().getMetadata().getModule();

        if (StringUtils.isEmpty(glue)) {
            return hookReport.getDefinitionReport().getLocation();
        }

        return glue + "." + hookReport.getDefinitionReport().getLocation();
    }

    /**
     * Contains any of step results included in this run images?
     *
     * @return true when there is at least one image,
     * false otherwise.
     */
    public boolean renderShowPicturesButton(ScenarioRunReport runReport) {
        Function<List<? extends ResultReport>, Boolean> containsImg = stepResults ->
                stepResults.stream().anyMatch(o -> !o.getEmbeddingList().isEmpty());

        return !imagesIncluded
                && (containsImg.apply(runReport.getBeforeHookStepResults())
                || containsImg.apply(runReport.getBackgroundStepResults())
                || containsImg.apply(runReport.getScenarioStepResults())
                || containsImg.apply(runReport.getAfterHookStepResults()));
    }

    /**
     * Create url to console, where build logs are held.
     *
     * @return create url for to logs.
     */
    public String createConsoleLink() {
        BuildRunMetadata metadata = featureReport.getBuildRunMetadataWithId().getMetadata();
        Map<String, String> placeholderValues = new HashMap<>();
        placeholderValues.put("projectName", metadata.getProjectName());
        placeholderValues.put("sequentialNumber", String.valueOf(metadata.getSequentialNumber()));

        String consoleUrlPattern = configService.retrieveApplicationConfig().getConsoleUrlPattern();

        return new StrSubstitutor(placeholderValues).replace(consoleUrlPattern);
    }
}