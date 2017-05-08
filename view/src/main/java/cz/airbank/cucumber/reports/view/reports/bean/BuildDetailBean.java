package cz.airbank.cucumber.reports.view.reports.bean;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import cz.airbank.cucumber.reports.view.model.TestSuiteWithFeaturesMetadata;
import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadata;
import cz.airbank.cucumber.reports.view.reports.service.ReportsService;
import cz.airbank.cucumber.reports.view.reports.service.model.BuildRunReport;
import cz.airbank.cucumber.reports.view.service.JSFUtilsService;

/**
 * Request scoped bean which has functionality for build detail.
 *
 * @author David Passler
 */
@Named
@Scope(org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST)
@Component
public class BuildDetailBean {

    /**
     * Name of URL parameter with build id.
     */
    public static final String BUILD_ID_PARAM_NAME = "buildId";

    /**
     * Outcome for build detail page backed by this bean.
     */
    public static final String BUILD_DETAIL_OUTCOME = "toBuildDetail";

    private final ReportsService reportsService;
    private final JSFUtilsService jsfUtilsService;

    private BuildRunReport buildRunReport;

    /**
     * Instantiates a new Build detail bean.
     */
    @Autowired
    public BuildDetailBean(ReportsService reportsService, JSFUtilsService jsfUtilsService) {
        this.reportsService = reportsService;
        this.jsfUtilsService = jsfUtilsService;
    }

    /**
     * Gets data.
     */
    @PostConstruct
    void getData() {
        final String buildId = jsfUtilsService.getPageParameter(BUILD_ID_PARAM_NAME);

        if (StringUtils.isEmpty(buildId)) {
            throw new IllegalArgumentException("No '" + buildId + "' specified.");
        }

        buildRunReport = reportsService.findBuildRunReportById(buildId);
        Assert.notNull(buildRunReport);
    }

    /**
     * Report about build run.
     *
     * @return always not {@code null}
     */
    public BuildRunReport getBuildRunReport() {
        return buildRunReport;
    }

    /**
     * Shortcut method for build run metadata of currently reported build.
     */
    public BuildRunMetadata getBuildRunMetadata() {
        return buildRunReport.getCurrentBuildRunWithId().getMetadata();
    }

    /**
     * Compute ratio of passed tests.
     *
     * @param suiteWithFeaturesMetadata to compute from
     * @return ratio in range <0, 1>
     */
    public double computeTestsPassedRatio(TestSuiteWithFeaturesMetadata suiteWithFeaturesMetadata) {
        return suiteWithFeaturesMetadata.computeTestsPassed() / (double) suiteWithFeaturesMetadata.computeTestsTotal();
    }
}