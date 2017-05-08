package cz.airbank.cucumber.reports.dao.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Transport object for test suites within
 *
 * @author Vaclav Stengl
 */
public class ProjectWithRunsTo implements Serializable {

    private static final long serialVersionUID = -4996161209799604660L;

    private String projectName;
    private List<BuildRunWithTestSuitesTo> buildRunWithTestSuitesTos;

    /**
     * The name of project.
     */
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Build runs with project name eq {@link #getProjectName()}.
     */
    public List<BuildRunWithTestSuitesTo> getBuildRunWithTestSuitesTos() {
        if (buildRunWithTestSuitesTos == null) {
            buildRunWithTestSuitesTos = new ArrayList<>();
        }

        return buildRunWithTestSuitesTos;
    }

    public void setBuildRunWithTestSuitesTos(List<BuildRunWithTestSuitesTo> buildRunWithTestSuitesTos) {
        this.buildRunWithTestSuitesTos = buildRunWithTestSuitesTos;
    }
}
