package cz.airbank.cucumber.reports.view.reports.model;

import java.io.Serializable;

/**
 * Model object for count of features, scenarios and steps in module.
 *
 * @author David Passler
 */
public class DefinitionPerModuleCount implements Serializable {
    private static final long serialVersionUID = 3841232597989006257L;

    /**
     * Name of the module.
     */
    private String moduleName;

    /**
     * Count of features.
     */
    private int featureCount;

    /**
     * Count of scenarios.
     */
    private int scenarioCount;

    /**
     * Count of steps.
     */
    private int stepCount;

    /**
     * Gets module name.
     *
     * @return the module name
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * Sets module name.
     *
     * @param moduleName the module name
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * Gets feature count.
     *
     * @return the feature count
     */
    public int getFeatureCount() {
        return featureCount;
    }

    /**
     * Sets feature count.
     *
     * @param featureCount the feature count
     */
    public void setFeatureCount(int featureCount) {
        this.featureCount = featureCount;
    }

    /**
     * Gets scenario count.
     *
     * @return the scenario count
     */
    public int getScenarioCount() {
        return scenarioCount;
    }

    /**
     * Sets scenario count.
     *
     * @param scenarioCount the scenario count
     */
    public void setScenarioCount(int scenarioCount) {
        this.scenarioCount = scenarioCount;
    }

    /**
     * Gets step count.
     *
     * @return the step count
     */
    public int getStepCount() {
        return stepCount;
    }

    /**
     * Sets step count.
     *
     * @param stepCount the step count
     */
    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }
}
