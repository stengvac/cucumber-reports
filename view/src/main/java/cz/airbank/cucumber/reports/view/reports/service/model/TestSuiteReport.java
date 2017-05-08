package cz.airbank.cucumber.reports.view.reports.service.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadataWithId;
import cz.airbank.cucumber.reports.view.reports.model.testsuite.TestSuiteMetadataWithId;

/**
 * View object for test suite report.
 *
 * @author Vaclav Stengl
 */
public class TestSuiteReport implements Serializable {

    private static final long serialVersionUID = 2957466644337181050L;

    private BuildRunMetadataWithId buildRunMetadataWithId;
    private TestSuiteMetadataWithId testSuiteMetadataWithId;
    private List<FeaturesInModule> featuresInModules;

    /**
     * Build run metadata and id of build run, which contains reported test suite.
     *
     * @return always not {@code null}
     */
    public BuildRunMetadataWithId getBuildRunMetadataWithId() {
        return buildRunMetadataWithId;
    }

    public void setBuildRunMetadataWithId(BuildRunMetadataWithId buildRunMetadataWithId) {
        this.buildRunMetadataWithId = buildRunMetadataWithId;
    }

    /**
     * Test suite metadata and id of reported test suite.
     *
     * @return always not {@code null}
     */
    public TestSuiteMetadataWithId getTestSuiteMetadataWithId() {
        return testSuiteMetadataWithId;
    }

    public void setTestSuiteMetadataWithId(TestSuiteMetadataWithId testSuiteMetadataWithId) {
        this.testSuiteMetadataWithId = testSuiteMetadataWithId;
    }

    /**
     * Features inside test suite grouped by modules.
     *
     * @return always not {@code null}
     */
    public List<FeaturesInModule> getFeaturesInModules() {
        if (featuresInModules == null) {
            featuresInModules = new ArrayList<>();
        }

        return featuresInModules;
    }

    public void setFeaturesInModules(List<FeaturesInModule> featuresInModules) {
        this.featuresInModules = featuresInModules;
    }
}
