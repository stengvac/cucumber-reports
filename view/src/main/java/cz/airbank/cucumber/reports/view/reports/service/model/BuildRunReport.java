package cz.airbank.cucumber.reports.view.reports.service.model;

import java.io.Serializable;
import java.util.List;

import cz.airbank.cucumber.reports.view.model.TestSuiteWithFeaturesMetadata;
import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadataWithId;

/**
 * View object representing build run report.
 *
 * @author Vaclav Stengl
 */
public class BuildRunReport implements Serializable {

    private static final long serialVersionUID = -4920887756978776237L;

    private BuildRunMetadataWithId previousBuildRunWithId;
    private BuildRunMetadataWithId currentBuildRunWithId;
    private BuildRunMetadataWithId nextMetadataWithId;
    private List<TestSuiteWithFeaturesMetadata> testSuiteWithFeaturesMetadata;

    /**
     * Build run metadata and id of build, which was executed before this one.
     *
     * @return {@code null} if there was not previous build
     */
    public BuildRunMetadataWithId getPreviousBuildRunWithId() {
        return previousBuildRunWithId;
    }

    public void setPreviousBuildRunWithId(BuildRunMetadataWithId previousBuildRunWithId) {
        this.previousBuildRunWithId = previousBuildRunWithId;
    }

    /**
     * Build run metadata and id reported build run.
     *
     * @return always not {@code null}
     */
    public BuildRunMetadataWithId getCurrentBuildRunWithId() {
        return currentBuildRunWithId;
    }

    public void setCurrentBuildRunWithId(BuildRunMetadataWithId currentBuildRunWithId) {
        this.currentBuildRunWithId = currentBuildRunWithId;
    }

    /**
     * Build run metadata and id of build, which was executed after this one.
     *
     * @return {@code null} if there was not previous build
     */
    public BuildRunMetadataWithId getNextMetadataWithId() {
        return nextMetadataWithId;
    }

    public void setNextMetadataWithId(BuildRunMetadataWithId nextMetadataWithId) {
        this.nextMetadataWithId = nextMetadataWithId;
    }

    /**
     * Test suite executions in this build run.
     */
    public List<TestSuiteWithFeaturesMetadata> getTestSuiteWithFeaturesMetadata() {
        return testSuiteWithFeaturesMetadata;
    }

    public void setTestSuiteWithFeaturesMetadata(List<TestSuiteWithFeaturesMetadata> testSuiteWithFeaturesMetadata) {
        this.testSuiteWithFeaturesMetadata = testSuiteWithFeaturesMetadata;
    }
}
