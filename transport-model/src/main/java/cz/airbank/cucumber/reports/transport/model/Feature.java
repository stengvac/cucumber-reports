package cz.airbank.cucumber.reports.transport.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent {@code Feature} tag inside Cucumber .feature file.
 *
 * @author Vaclav Stengl
 */
//TODO add Jackson databind annotations
public class Feature extends TagStatement {

    private static final long serialVersionUID = -3883297836904635122L;

    private List<ScenarioDefinition> scenarioDefinitionList;
    private ScenarioDefinition background;
    private BuildRunMetadata buildRunMetadata;
    private TestSuiteMetadata testSuiteMetadata;
    private FeatureMetadata featureMetadata;

    /**
     * Provide background instance or null if .feature file does not contain {@code Background} section.
     */
    public ScenarioDefinition getBackground() {
        return background;
    }

    public void setBackground(ScenarioDefinition background) {
        this.background = background;
    }

    /**
     * Metadata about build run
     */
    public BuildRunMetadata getBuildRunMetadata() {
        return buildRunMetadata;
    }

    public void setBuildRunMetadata(BuildRunMetadata buildRunMetadata) {
        this.buildRunMetadata = buildRunMetadata;
    }

    /**
     * Scenario definitions inside *.feature file.
     */
    public List<ScenarioDefinition> getScenarioDefinitionList() {
        if (scenarioDefinitionList == null) {
            scenarioDefinitionList = new ArrayList<>();
        }

        return scenarioDefinitionList;
    }

    /**
     * Feature metadata
     */
    public FeatureMetadata getFeatureMetadata() {
        return featureMetadata;
    }

    public void setFeatureMetadata(FeatureMetadata featureMetadata) {
        this.featureMetadata = featureMetadata;
    }

    /**
     * Metadata about test suite execution, which executed this feature
     */
    public TestSuiteMetadata getTestSuiteMetadata() {
        return testSuiteMetadata;
    }

    public void setTestSuiteMetadata(TestSuiteMetadata testSuiteMetadata) {
        this.testSuiteMetadata = testSuiteMetadata;
    }
}
