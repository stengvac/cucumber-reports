package cz.airbank.cucumber.reports.dao.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Transport object for build run with basic info about test suite.
 *
 * @author Vaclav Stengl
 */
public class BuildRunWithTestSuitesTo implements Serializable {

    private static final long serialVersionUID = 2358265133510841212L;

    private BuildRunMetadataWithIdTo buildRunMetadataWithIdTo;
    private List<TestSuiteWithFeaturesMetadataTo> testSuiteWithFeaturesMetadataToes;

    /**
     * Test suites with included info about executed features so its possible to create a lot of
     * different data views.
     */
    public List<TestSuiteWithFeaturesMetadataTo> getTestSuiteWithFeaturesMetadataToes() {
        if (testSuiteWithFeaturesMetadataToes == null) {
            testSuiteWithFeaturesMetadataToes = new ArrayList<>();
        }

        return testSuiteWithFeaturesMetadataToes;
    }


    public void setTestSuiteWithFeaturesMetadataToes(
            List<TestSuiteWithFeaturesMetadataTo> testSuiteWithFeaturesMetadataToes) {
        this.testSuiteWithFeaturesMetadataToes = testSuiteWithFeaturesMetadataToes;
    }

    /**
     * Build run metadata with id.
     */
    public BuildRunMetadataWithIdTo getBuildRunMetadataWithIdTo() {
        return buildRunMetadataWithIdTo;
    }

    public void setBuildRunMetadataWithIdTo(BuildRunMetadataWithIdTo buildRunMetadataWithIdTo) {
        this.buildRunMetadataWithIdTo = buildRunMetadataWithIdTo;
    }
}
