package cz.airbank.cucumber.reports.view.reports.model.feature;

import java.util.ArrayList;
import java.util.List;

import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadataWithId;
import cz.airbank.cucumber.reports.view.reports.model.testsuite.TestSuiteMetadataWithId;

/**
 * Class representing reported feature
 *
 * @author Vaclav Stengl
 */
public class FeatureReport extends TaggedStatementReport {

    private static final long serialVersionUID = 829750347190144475L;

    private List<ScenarioDefinitionReport> scenarioDefinitionList;
    private ScenarioDefinitionReport background;
    private BuildRunMetadataWithId buildRunMetadataWithId;
    private TestSuiteMetadataWithId testSuiteMetadataWithId;
    private FeatureMetadataWithId featureMetadataWithId;

    /**
     * Provide background instance or null if .feature file does not contain {@code Background} section.
     */
    public ScenarioDefinitionReport getBackground() {
        return background;
    }

    public void setBackground(ScenarioDefinitionReport background) {
        this.background = background;
    }

    /**
     * Metadata about build run
     */
    public BuildRunMetadataWithId getBuildRunMetadataWithId() {
        return buildRunMetadataWithId;
    }

    public void setBuildRunMetadataWithId(BuildRunMetadataWithId buildRunMetadataWithId) {
        this.buildRunMetadataWithId = buildRunMetadataWithId;
    }

    /**
     * Scenario definitions inside *.feature file.
     */
    public List<ScenarioDefinitionReport> getScenarioDefinitionList() {
        if (scenarioDefinitionList == null) {
            scenarioDefinitionList = new ArrayList<>();
        }

        return scenarioDefinitionList;
    }

    /**
     * Feature metadata
     */
    public FeatureMetadataWithId getFeatureMetadataWithId() {
        return featureMetadataWithId;
    }

    public void setFeatureMetadataWithId(FeatureMetadataWithId featureMetadataWithId) {
        this.featureMetadataWithId = featureMetadataWithId;
    }

    /**
     * Metadata about test suite execution, which executed this feature
     */
    public TestSuiteMetadataWithId getTestSuiteMetadataWithId() {
        return testSuiteMetadataWithId;
    }

    public void setTestSuiteMetadataWithId(TestSuiteMetadataWithId testSuiteMetadataWithId) {
        this.testSuiteMetadataWithId = testSuiteMetadataWithId;
    }
}
