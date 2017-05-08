package cz.airbank.cucumber.reports.view.model;

/**
 * Counts for statistics results.
 *
 * @author David Passler
 */
public class StatisticsResultCounts {

    /**
     * Count of passed features.
     */
    private int featurePassedCount;

    /**
     * Count of failed features.
     */
    private int featureFailedCount;

    /**
     * Count of passed scenarios.
     */
    private int scenarioPassedCount;

    /**
     * Count of total scenarios.
     */
    private int scenarioTotalCount;

    /**
     * Sum of all durations of features from the module.
     */
    private long featureSumDuration;

    /**
     * Gets feature passed count.
     *
     * @return the feature passed count
     */
    public int getFeaturePassedCount() {
        return featurePassedCount;
    }

    /**
     * Increases feature passed count.
     */
    public void increaseFeaturePassedCount() {
        this.featurePassedCount++;
    }

    /**
     * Gets feature failed count.
     *
     * @return the feature failed count
     */
    public int getFeatureFailedCount() {
        return featureFailedCount;
    }

    /**
     * Increase feature failed count.
     */
    public void increaseFeatureFailedCount() {
        this.featureFailedCount++;
    }

    /**
     * Gets scenario passed count.
     *
     * @return the scenario passed count
     */
    public int getScenarioPassedCount() {
        return scenarioPassedCount;
    }

    /**
     * Increase scenario passed count.
     */
    public void addScenarioPassedCount(int toAdd) {
        this.scenarioPassedCount += toAdd;
    }

    /**
     * Gets scenario failed count.
     *
     * @return the scenario total count
     */
    public int getScenarioTotalCount() {
        return scenarioTotalCount;
    }

    /**
     * Increase scenario total count.
     */
    public void addScenarioTotalCount(int toAdd) {
        this.scenarioTotalCount += toAdd;
    }

    /**
     * Gets feature duration.
     *
     * @return the feature duration
     */
    public long getFeatureSumDuration() {
        return featureSumDuration;
    }

    /**
     * Add feature duration.
     *
     * @param featureDurationToAdd the feature duration to add
     */
    public void addFeatureDuration(long featureDurationToAdd) {
        this.featureSumDuration += featureDurationToAdd;
    }

}
