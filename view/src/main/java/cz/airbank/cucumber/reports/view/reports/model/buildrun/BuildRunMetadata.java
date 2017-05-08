package cz.airbank.cucumber.reports.view.reports.model.buildrun;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Metadata about build run
 *
 * @author Vaclav Stengl
 */
public class BuildRunMetadata {

    private String projectName;
    private LocalDateTime buildAt;
    private long sequentialNumber;
    private List<String> tags;
    private String executedBy;
    private String environmentName;
    private boolean passed;

    /**
     * The sequential number of build run.
     */
    public long getSequentialNumber() {
        return sequentialNumber;
    }

    public void setSequentialNumber(long sequentialNumber) {
        this.sequentialNumber = sequentialNumber;
    }

    /**
     * Name of the project of this build.
     */
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Build run performed at.
     */
    public LocalDateTime getBuildAt() {
        return buildAt;
    }

    public void setBuildAt(LocalDateTime buildAt) {
        this.buildAt = buildAt;
    }

    /**
     * Tags used to determine whenever build should be used for statistical purposes.
     */
    public List<String> getTags() {
        if (tags == null) {
            tags = new ArrayList<>();
        }

        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Username of build run executioner.
     */
    public String getExecutedBy() {
        return executedBy;
    }

    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    /**
     * Name of environment where tests were executed. Like devXX, uatXX etc.
     */
    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    /**
     * Was build run success?
     */
    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
