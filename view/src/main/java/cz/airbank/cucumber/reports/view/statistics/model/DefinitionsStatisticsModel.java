package cz.airbank.cucumber.reports.view.statistics.model;

import java.io.Serializable;

/**
 * Model object for count of features, scenarios and steps in module.
 *
 * @author David Passler
 */
public class DefinitionsStatisticsModel implements Serializable {
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
     * Add feature count.
     *
     * @param featureCountToAdd the feature count to add
     */
    public void addFeatureCount(int featureCountToAdd) {
        this.featureCount += featureCountToAdd;
    }

    /**
     * Add scenario count.
     *
     * @param scenarioCountToAdd the scenario count to add
     */
    public void addScenarioCount(int scenarioCountToAdd) {
        this.scenarioCount += scenarioCountToAdd;
    }

}
