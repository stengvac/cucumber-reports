package cz.airbank.cucumber.reports.view.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadataWithId;
import cz.airbank.cucumber.reports.view.reports.model.testsuite.TestSuiteMetadataWithId;

/**
 * View object with counts used to create basic statistical views.
 *
 * @author Vaclav Stengl
 */
public class BuildRunWithCounts implements Serializable {

    private static final long serialVersionUID = 8200647166168833501L;

    private BuildRunMetadataWithId metadataWithId;
    private List<TestSuiteWithFeaturesMetadata> testSuiteWithCountsList;
    private int scenarioExecutionsTotal;
    private int scenarioExecutionsPassed;

    /**
     * Build metadata with id.
     */
    public BuildRunMetadataWithId getMetadataWithId() {
        return metadataWithId;
    }

    public void setMetadataWithId(BuildRunMetadataWithId metadataWithId) {
        this.metadataWithId = metadataWithId;
    }

    /**
     * Test suites with additional information about executed features.
     */
    public List<TestSuiteWithFeaturesMetadata> getTestSuiteWithCountsList() {
        if (testSuiteWithCountsList == null) {
            testSuiteWithCountsList = new ArrayList<>();
        }

        return testSuiteWithCountsList;
    }

    public void setTestSuiteWithCountsList(List<TestSuiteWithFeaturesMetadata> testSuiteWithCountsList) {
        this.testSuiteWithCountsList = testSuiteWithCountsList;
    }

    /**
     * Total count of executed scenarios within all test suites
     */
    public int getScenarioExecutionsTotal() {
        return scenarioExecutionsTotal;
    }

    public void setScenarioExecutionsTotal(int scenarioExecutionsTotal) {
        this.scenarioExecutionsTotal = scenarioExecutionsTotal;
    }

    /**
     * Total count of passed executed scenarios.
     */
    public int getScenarioExecutionsPassed() {
        return scenarioExecutionsPassed;
    }

    public void setScenarioExecutionsPassed(int scenarioExecutionsPassed) {
        this.scenarioExecutionsPassed = scenarioExecutionsPassed;
    }

    /**
     * Return true when build run contains only one test suite.
     */
    public boolean containsOnlyOneTestSuite() {
        return getTestSuiteWithCountsList().size() == 1;
    }

    /**
     * Return {@link TestSuiteMetadataWithId#getId()} of first test suite.
     * @return not {@code null} values
     */
    public String getIdOfFirstTestSuite() {
        return getTestSuiteWithCountsList().get(0).getTestSuiteMetadataWithId().getId();
    }
}
