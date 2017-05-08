package cz.airbank.cucumber.reports.view.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadata;
import cz.airbank.cucumber.reports.view.reports.model.testsuite.TestSuiteMetadataWithId;

/**
 * Basic information about test suite and included features.
 *
 * @author Vaclav Stengl
 */
public class TestSuiteWithFeaturesMetadata implements Serializable {

    private static final long serialVersionUID = -5323552943042796554L;

    private TestSuiteMetadataWithId testSuiteMetadataWithId;
    private List<FeatureMetadata> featureMetadataList;

    /**
     * Metadata about test suite execution.
     */
    public TestSuiteMetadataWithId getTestSuiteMetadataWithId() {
        return testSuiteMetadataWithId;
    }

    public void setTestSuiteMetadataWithId(TestSuiteMetadataWithId testSuiteMetadataWithId) {
        this.testSuiteMetadataWithId = testSuiteMetadataWithId;
    }

    /**
     * Feature runs within test suite.
     * Used to create some basic statistics.
     */
    public List<FeatureMetadata> getFeatureMetadataList() {
        if (featureMetadataList == null) {
            featureMetadataList = new ArrayList<>();
        }

        return featureMetadataList;
    }

    public void setFeatureMetadataList(List<FeatureMetadata> featureMetadataList) {
        this.featureMetadataList = featureMetadataList;
    }

    /**
     * Test suite passed?
     *
     * @return {@code true} when all features inside passed
     */
    public boolean passed() {
        for (FeatureMetadata featureMetadata : getFeatureMetadataList()) {
            if (!featureMetadata.passed()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Compute total number of passed tests within tests suite.
     */
    public int computeTestsPassed() {
        return sumOf(FeatureMetadata::getScenarioExecutionsPassed);
    }

    /**
     * Compute total number of tests within tests suite.
     */
    public int computeTestsTotal() {
        return sumOf(FeatureMetadata::getScenarioExecutionsTotal);
    }

    /**
     * Compute sum of via provided function.
     *
     * @param sumFunction to sum by
     */
    private int sumOf(Function<FeatureMetadata, Integer> sumFunction) {
        return getFeatureMetadataList().stream().mapToInt(sumFunction::apply).sum();
    }
}
