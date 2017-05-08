package cz.airbank.cucumber.reports.view.reports.bean;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadata;
import cz.airbank.cucumber.reports.view.reports.model.testsuite.TestSuiteMetadata;
import cz.airbank.cucumber.reports.view.reports.service.ReportsService;
import cz.airbank.cucumber.reports.view.reports.service.model.TestSuiteReport;
import cz.airbank.cucumber.reports.view.service.JSFUtilsService;

/**
 * Backing bean for test suite detail page.
 *
 * @author Vaclav Stengl
 */
@Named
@Scope(org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST)
public class TestSuiteBean {

    /**
     * Required URL parameter for page backed by this bean.
     * Value for this parameter is used to retrieve data
     * about build run which hold this test suite.
     */
    public static final String BUILD_ID_PARAM_NAME = "buildId";

    /**
     * Required URL parameter for page backed by this bean.
     * Value in this parameter is used to retrieve test suite.
     */
    public static final String TEST_SUITE_PARAM_NAME = "suiteId";

    /**
     * Outcome used to redirect to test suite report page.
     * This constant must be same as outcome used in {@code faces-config.xml}.
     */
    public static final String TEST_SUITE_REPORT_OUTCOME = "toTestSuiteReport";

    private final ReportsService reportsService;
    private final JSFUtilsService jsfUtilsService;

    private TestSuiteReport testSuiteReport;

    @Autowired
    public TestSuiteBean(ReportsService reportsService, JSFUtilsService jsfUtilsService) {
        this.reportsService = reportsService;
        this.jsfUtilsService = jsfUtilsService;
    }

    @PostConstruct
    public void init() {
        String buildId = jsfUtilsService.getPageParameter(BUILD_ID_PARAM_NAME);
        String testSuiteId = jsfUtilsService.getPageParameter(TEST_SUITE_PARAM_NAME);

        Assert.notNull(buildId, "Build id can't be null.");
        Assert.notNull(testSuiteId, "Test suite id can't be null.");

        testSuiteReport = reportsService.findTestSuiteReport(buildId, testSuiteId);

        Assert.notNull(testSuiteReport);
    }

    /**
     * Test suite to report.
     */
    public TestSuiteReport getTestSuiteReport() {
        return testSuiteReport;
    }

    /**
     * Shortcut method for feature metadata.
     */
    public BuildRunMetadata getReportMetadata() {
        return testSuiteReport.getBuildRunMetadataWithId().getMetadata();
    }

    /**
     * Shortcut for test suite metadata.
     */
    public TestSuiteMetadata getTestSuiteMetadata() {
        return testSuiteReport.getTestSuiteMetadataWithId().getMetadata();
    }
}
