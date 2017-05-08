package cz.airbank.cucumber.reports.transport.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Metadata about build run
 *
 * @author Vaclav Stengl
 */
public class BuildRunMetadata implements Serializable {

    private static final long serialVersionUID = -7512170438866709412L;

    private String projectName;
    private LocalDateTime buildAt;
    private long sequentialNumber;
    private List<String> tags;
    private String executedBy;
    private String environmentName;

    /**
     * The sequential number of
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }

        BuildRunMetadata metadata = (BuildRunMetadata) obj;

        return Objects.equals(projectName, metadata.getProjectName())
               && Objects.equals(sequentialNumber, metadata.getSequentialNumber());
    }

    @Override
    public int hashCode() {
        int result = getProjectName().hashCode();
        result = 31 * result + (int) (getSequentialNumber() ^ (getSequentialNumber() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "BuildRunMetadata{" +
               "name='" + projectName + '\'' +
               ", buildAt=" + buildAt +
               ", sequentialNumber=" + sequentialNumber +
               ", statisticsTags=" + tags +
               ", executedBy='" + executedBy + '\'' +
               '}';
    }
}
