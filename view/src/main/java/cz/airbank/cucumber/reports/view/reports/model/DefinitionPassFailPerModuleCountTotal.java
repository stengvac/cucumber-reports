package cz.airbank.cucumber.reports.view.reports.model;

/**
 * Module object for count of passed/failed entities across all modules.
 *
 * @author David Passler
 */
public class DefinitionPassFailPerModuleCountTotal {

    /**
     * Count of all features that passed across all modules.
     */
    private int featurePassedCount;

    /**
     * Count of all features that failed across all modules.
     */
    private int featureFailedCount;

    /**
     * Count of all scenarios that passed across all modules.
     */
    private int scenarioPassedCount;

    /**
     * Count of all scenarios that failed across all modules.
     */
    private int scenarioFailedCount;

    /**
     * Count of all steps that passed across all modules.
     */
    private int stepPassedCount;

    /**
     * Count of all steps that failed across all modules.
     */
    private int stepFailedCount;

    /**
     * Count of all steps that were pending across all modules.
     */
    private int stepPendingCount;

    /**
     * Count of all steps that were skipped across all modules.
     */
    private int stepSkippedCount;

    /**
     * Count of all steps that were undefined across all modules.
     */
    private int stepUndefinedCount;

    /**
     * Count of all steps that were missing across all modules.
     */
    private int stepMissingCount;

    /**
     * Gets feature passed count.
     *
     * @return the feature passed count
     */
    public int getFeaturePassedCount() {
        return featurePassedCount;
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
     * Gets feature failed count.
     *
     * @return the feature failed count
     */
    public int getFeatureFailedCount() {
        return featureFailedCount;
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
     * Gets scenario passed count.
     *
     * @return the scenario passed count
     */
    public int getScenarioPassedCount() {
        return scenarioPassedCount;
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
     * Gets scenario failed count.
     *
     * @return the scenario failed count
     */
    public int getScenarioFailedCount() {
        return scenarioFailedCount;
    }

    /**
     * Add scenario failed count.
     *
     * @param scenarioFailedCountToAdd the scenario failed count to add
     */
    public void addScenarioFailedCount(int scenarioFailedCountToAdd) {
        this.scenarioFailedCount += scenarioFailedCountToAdd;
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
     * Add step passed count.
     *
     * @param stepPassedCountToAdd the step passed count to add
     */
    public void addStepPassedCount(int stepPassedCountToAdd) {
        this.stepPassedCount += stepPassedCountToAdd;
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
     * Add step failed count.
     *
     * @param stepFailedCountToAdd the step failed count to add
     */
    public void addStepFailedCount(int stepFailedCountToAdd) {
        this.stepFailedCount += stepFailedCountToAdd;
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
     * Add step pending count.
     *
     * @param stepPendingCountToAdd the step pending count to add
     */
    public void addStepPendingCount(int stepPendingCountToAdd) {
        this.stepPendingCount += stepPendingCountToAdd;
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
     * Add step skipped count.
     *
     * @param stepSkippedCountToAdd the step skipped count to add
     */
    public void addStepSkippedCount(int stepSkippedCountToAdd) {
        this.stepSkippedCount += stepSkippedCountToAdd;
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
     * Add step undefined count.
     *
     * @param stepUndefinedCount the step undefined count
     */
    public void addStepUndefinedCount(int stepUndefinedCount) {
        this.stepUndefinedCount += stepUndefinedCount;
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
     * Add step missing count.
     *
     * @param stepMissingCountToAdd the step missing count to add
     */
    public void addStepMissingCount(int stepMissingCountToAdd) {
        this.stepMissingCount += stepMissingCountToAdd;
    }
}
