package cz.airbank.cucumber.reports.view.statistics.model;

import java.io.Serializable;

/**
 * Model object for module pass/fail counts.
 *
 * @author David Passler
 */
public class ResultsStatisticsModel implements Serializable {

    private static final long serialVersionUID = -3995612538432807998L;

    private String moduleName;
    private int featurePassedCount;
    private int featureFailedCount;
    private int scenarioPassedCount;
    private int scenarioTotalCount;
    private long featureExecutionDuration;

    /**
     * The module name.
     */
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * Count of passed features.
     */
    public int getFeaturePassedCount() {
        return featurePassedCount;
    }

    public void setFeaturePassedCount(int featurePassedCount) {
        this.featurePassedCount = featurePassedCount;
    }

    /**
     * Count of failed features.
     */
    public int getFeatureFailedCount() {
        return featureFailedCount;
    }

    public void setFeatureFailedCount(int featureFailedCount) {
        this.featureFailedCount = featureFailedCount;
    }

    /**
     * Count of passed scenarios.
     */
    public int getScenarioPassedCount() {
        return scenarioPassedCount;
    }

    public void setScenarioPassedCount(int scenarioPassedCount) {
        this.scenarioPassedCount = scenarioPassedCount;
    }

    /**
     * Count of failed scenarios.
     */
    public int getScenarioTotalCount() {
        return scenarioTotalCount;
    }

    public void setScenarioTotalCount(int scenarioTotalCount) {
        this.scenarioTotalCount = scenarioTotalCount;
    }

    /**
     * Duration (nanos) of the feature.
     */
    public long getFeatureExecutionDuration() {
        return featureExecutionDuration;
    }

    public void setFeatureExecutionDuration(long featureExecutionDuration) {
        this.featureExecutionDuration = featureExecutionDuration;
    }

    /**
     * Add feature passed count.
     *
     * @param featurePassedCountToAdd the feature passed count to add
     */
    public void addFeaturePassedCount(int featurePassedCountToAdd) {
        this.featurePassedCount += featurePassedCountToAdd;
    }

    /**
     * Add feature failed count.
     *
     * @param featureFailedCountToAdd the feature failed count to add
     */
    public void addFeatureFailedCount(int featureFailedCountToAdd) {
        this.featureFailedCount += featureFailedCountToAdd;
    }


    /**
     * Add scenario passed count.
     *
     * @param scenarioPassedCountToAdd the scenario passed count to add
     */
    public void addScenarioPassedCount(int scenarioPassedCountToAdd) {
        this.scenarioPassedCount += scenarioPassedCountToAdd;
    }


    /**
     * Add scenario failed count.
     *
     * @param scenarioTotalCountToAdd the scenario total count to add
     */
    public void addScenarioTotalCount(int scenarioTotalCountToAdd) {
        this.scenarioTotalCount += scenarioTotalCountToAdd;
    }

    /**
     * Compute total feature count.
     */
    public int computeTotalFeatureCount() {
        return featureFailedCount + featurePassedCount;
    }

    /**
     * Compute total scenario count.
     */
    public int computeTotalScenarioCount() {
        return scenarioTotalCount;
    }
}
