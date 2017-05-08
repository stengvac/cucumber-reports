package cz.airbank.cucumber.reports.view.reports.model;

/**
 * Module object for count of entities across all modules.
 *
 * @author David Passler
 */
public class DefinitionPerModuleCountTotal {

    /**
     * Count of features across all modules.
     */
    private int featureTotalCount;

    /**
     * Count of scenarios across all modules.
     */
    private int scenarioTotalCount;

    /**
     * Count of steps across all modules.
     */
    private int stepTotalCount;

    /**
     * Gets feature total count.
     *
     * @return the feature total count
     */
    public int getFeatureTotalCount() {
        return featureTotalCount;
    }

    /**
     * Add feature total count.
     *
     * @param featureTotalCountToAdd the feature total count to add
     */
    public void addFeatureTotalCount(int featureTotalCountToAdd) {
        this.featureTotalCount += featureTotalCountToAdd;
    }

    /**
     * Gets scenario total count.
     *
     * @return the scenario total count
     */
    public int getScenarioTotalCount() {
        return scenarioTotalCount;
    }

    /**
     * Add scenario total count.
     *
     * @param scenarioTotalCountToAdd the scenario total count to add
     */
    public void addScenarioTotalCount(int scenarioTotalCountToAdd) {
        this.scenarioTotalCount += scenarioTotalCountToAdd;
    }

    /**
     * Gets step total count.
     *
     * @return the step total count
     */
    public int getStepTotalCount() {
        return stepTotalCount;
    }

    /**
     * Add step total count.
     *
     * @param stepTotalCountToAdd the step total count to add
     */
    public void addStepTotalCount(int stepTotalCountToAdd) {
        this.stepTotalCount += stepTotalCountToAdd;
    }
}
