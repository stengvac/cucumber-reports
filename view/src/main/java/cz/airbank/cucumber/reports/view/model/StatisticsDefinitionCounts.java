package cz.airbank.cucumber.reports.view.model;

/**
 * Counts for statistics results.
 *
 * @author David Passler
 */
public class StatisticsDefinitionCounts {

    /**
     * How many unique features were run.
     */
    private int featureCount;

    /**
     * How many unique scenarios were run.
     */
    private int scenarioCount;

    /**
     * Gets feature count.
     *
     * @return the feature count
     */
    public int getFeatureCount() {
        return featureCount;
    }

    /**
     * Increase feature count by one.
     */
    public void increaseFeatureCount() {
        this.featureCount++;
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
     * Increase scenario count by given amount.
     *
     * @param amount the amount
     */
    public void increaseScenarioCountBy(int amount) {
        this.scenarioCount += amount;
    }
}
