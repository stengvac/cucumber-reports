package cz.airbank.cucumber.reports.view.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadata;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadataWithId;

/**
 * Model object with counts for specific module.
 *
 * @author David Passler
 */
public class ModuleBuildCounts implements Serializable {

    private static final long serialVersionUID = 1285998827316430511L;

    private String moduleName;
    private BuildRunMetadata buildRunMetadata;
    private final List<FeatureMetadataWithId> featureWithResults;
    private int featureCount;
    private int scenarioCount;
    private int stepCount;

    public ModuleBuildCounts() {
        featureWithResults = new ArrayList<>();
    }

    /**
     * The module name for which are the counts obtained.
     */
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * The list with feature names and their results.
     */
    public List<FeatureMetadataWithId> getFeatureWithResults() {
        return featureWithResults;
    }

    /**
     * Count of features in this module affected.
     */
    public int getFeatureCount() {
        return featureCount;
    }

    public void setFeatureCount(int featureCount) {
        this.featureCount = featureCount;
    }

    /**
     * Increase feature count.
     */
    public void increaseFeatureCount() {
        this.featureCount++;
    }

    /**
     * Count of scenarios in this module affected.
     */
    public int getScenarioCount() {
        return scenarioCount;
    }

    public void setScenarioCount(int scenarioCount) {
        this.scenarioCount = scenarioCount;
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
     * Count of steps in this module affected.
     */
    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    /**
     * Increase step count by.
     *
     * @param toAdd the to add
     */
    public void increaseStepCountBy(int toAdd) {
        this.stepCount += toAdd;
    }

    /**
     * Build run meta data
     */
    public BuildRunMetadata getBuildRunMetadata() {
        return buildRunMetadata;
    }

    public void setBuildRunMetadata(BuildRunMetadata buildRunMetadata) {
        this.buildRunMetadata = buildRunMetadata;
    }
}
