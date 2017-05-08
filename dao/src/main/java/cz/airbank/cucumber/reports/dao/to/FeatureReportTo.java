package cz.airbank.cucumber.reports.dao.to;

import java.io.Serializable;

import cz.airbank.cucumber.reports.dao.entity.FeatureRun;

/**
 * To for feature report.
 *
 * @author Vaclav Stengl
 */
public class FeatureReportTo implements Serializable {

    private static final long serialVersionUID = -2515593045190784203L;

    private BuildRunMetadataWithIdTo buildRunMetadataWithIdTo;
    private TestSuiteMetadataWithIdTo testSuiteMetadataWithIdTo;
    private FeatureRun featureRun;

    /**
     * Test suite presentation to.
     * Contains some basic info about test suite, which hold inside this feature run.
     */
    public TestSuiteMetadataWithIdTo getTestSuiteMetadataWithIdTo() {
        return testSuiteMetadataWithIdTo;
    }

    public void setTestSuiteMetadataWithIdTo(TestSuiteMetadataWithIdTo testSuiteMetadataWithIdTo) {
        this.testSuiteMetadataWithIdTo = testSuiteMetadataWithIdTo;
    }

    /**
     * The data to report.
     */
    public FeatureRun getFeatureRun() {
        return featureRun;
    }

    public void setFeatureRun(FeatureRun featureRun) {
        this.featureRun = featureRun;
    }

    /**
     * Basic information about build run.
     */
    public BuildRunMetadataWithIdTo getBuildRunMetadataWithIdTo() {
        return buildRunMetadataWithIdTo;
    }

    public void setBuildRunMetadataWithIdTo(BuildRunMetadataWithIdTo buildRunMetadataWithIdTo) {
        this.buildRunMetadataWithIdTo = buildRunMetadataWithIdTo;
    }
}
