package cz.airbank.cucumber.reports.view.reports.model;

/**
 * Model object for module pass/fail counts.
 *
 * @author David Passler
 */
public class DefinitionPassFailPerModuleCount {
    /**
     * The module name.
     */
    private String moduleName;

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
     * Count of failed scenarios.
     */
    private int scenarioFailedCount;

    /**
     * Count of passed steps.
     */
    private int stepPassedCount;

    /**
     * Count of failed steps.
     */
    private int stepFailedCount;

    /**
     * Count of skipped steps.
     */
    private int stepSkippedCount;

    /**
     * Count of undefined steps.
     */
    private int stepUndefinedCount;

    /**
     * Count of missing steps.
     */
    private int stepMissingCount;

    /**
     * Count of pending steps.
     */
    private int stepPendingCount;

    /**
     * Duration of the feature.
     */
    private long featureDuration;

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
     * Gets feature passed count.
     *
     * @return the feature passed count
     */
    public int getFeaturePassedCount() {
        return featurePassedCount;
    }

    /**
     * Sets feature passed count.
     *
     * @param featurePassedCount the feature passed count
     */
    public void setFeaturePassedCount(int featurePassedCount) {
        this.featurePassedCount = featurePassedCount;
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
     * Sets feature failed count.
     *
     * @param featureFailedCount the feature failed count
     */
    public void setFeatureFailedCount(int featureFailedCount) {
        this.featureFailedCount = featureFailedCount;
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
     * Sets scenario passed count.
     *
     * @param scenarioPassedCount the scenario passed count
     */
    public void setScenarioPassedCount(int scenarioPassedCount) {
        this.scenarioPassedCount = scenarioPassedCount;
    }

    /**
     * Gets scenario failed count.
     *
     * @return the scenario failed count
     */
    public int getScenarioFailedCount() {
        return scenarioFailedCount;
    }

    /**
     * Sets scenario failed count.
     *
     * @param scenarioFailedCount the scenario failed count
     */
    public void setScenarioFailedCount(int scenarioFailedCount) {
        this.scenarioFailedCount = scenarioFailedCount;
    }

    /**
     * Gets step passed count.
     *
     * @return the step passed count
     */
    public int getStepPassedCount() {
        return stepPassedCount;
    }

    /**
     * Sets step passed count.
     *
     * @param stepPassedCount the step passed count
     */
    public void setStepPassedCount(int stepPassedCount) {
        this.stepPassedCount = stepPassedCount;
    }

    /**
     * Gets step failed count.
     *
     * @return the step failed count
     */
    public int getStepFailedCount() {
        return stepFailedCount;
    }

    /**
     * Sets step failed count.
     *
     * @param stepFailedCount the step failed count
     */
    public void setStepFailedCount(int stepFailedCount) {
        this.stepFailedCount = stepFailedCount;
    }

    /**
     * Gets step skipped count.
     *
     * @return the step skipped count
     */
    public int getStepSkippedCount() {
        return stepSkippedCount;
    }

    /**
     * Sets step skipped count.
     *
     * @param stepSkippedCount the step skipped count
     */
    public void setStepSkippedCount(int stepSkippedCount) {
        this.stepSkippedCount = stepSkippedCount;
    }

    /**
     * Gets step undefined count.
     *
     * @return the step undefined count
     */
    public int getStepUndefinedCount() {
        return stepUndefinedCount;
    }

    /**
     * Sets step undefined count.
     *
     * @param stepUndefinedCount the step undefined count
     */
    public void setStepUndefinedCount(int stepUndefinedCount) {
        this.stepUndefinedCount = stepUndefinedCount;
    }

    /**
     * Gets step missing count.
     *
     * @return the step missing count
     */
    public int getStepMissingCount() {
        return stepMissingCount;
    }

    /**
     * Sets step missing count.
     *
     * @param stepMissingCount the step missing count
     */
    public void setStepMissingCount(int stepMissingCount) {
        this.stepMissingCount = stepMissingCount;
    }

    /**
     * Gets feature duration.
     *
     * @return the feature duration
     */
    public long getFeatureDuration() {
        return featureDuration;
    }

    /**
     * Sets feature duration.
     *
     * @param featureDuration the feature duration
     */
    public void setFeatureDuration(long featureDuration) {
        this.featureDuration = featureDuration;
    }

    /**
     * Gets step pending count.
     *
     * @return the step pending count
     */
    public int getStepPendingCount() {
        return stepPendingCount;
    }

    /**
     * Sets step pending count.
     *
     * @param stepPendingCount the step pending count
     */
    public void setStepPendingCount(int stepPendingCount) {
        this.stepPendingCount = stepPendingCount;
    }
}
