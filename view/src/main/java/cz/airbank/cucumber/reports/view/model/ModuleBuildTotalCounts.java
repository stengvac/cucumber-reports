package cz.airbank.cucumber.reports.view.model;

/**
 * Total counts for module builds.
 *
 * @author David Passler
 */
public class ModuleBuildTotalCounts {

    /**
     * Total feature count.
     */
    private int featureCount;

    /**
     * Total scenario count.
     */
    private int scenarioCount;

    /**
     * Total step count.
     */
    private int stepCount;

    /**
     * Gets feature count.
     *
     * @return the feature count
     */
    public int getFeatureCount() {
        return featureCount;
    }

    /**
     * Increase feature count by.
     *
     * @param toAdd the to add
     */
    public void increaseFeatureCountBy(int toAdd) {
        this.featureCount += toAdd;
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
     * Increase scenario count by.
     *
     * @param toAdd the to add
     */
    public void increaseScenarioCountBy(int toAdd) {
        this.scenarioCount += toAdd;
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
     * Increase step count by.
     *
     * @param toAdd the to add
     */
    public void increaseStepCountBy(int toAdd) {
        this.stepCount += toAdd;
    }
}
