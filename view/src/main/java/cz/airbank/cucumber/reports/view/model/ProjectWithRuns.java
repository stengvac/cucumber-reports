package cz.airbank.cucumber.reports.view.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * View model containing info about last n project build runs.
 *
 * @author Vaclav Stengl
 */
public class ProjectWithRuns implements Serializable {

    private static final long serialVersionUID = 8369745520685212448L;

    private String projectName;
    private List<BuildRunWithCounts> buildRunWithCountsList;

    /**
     * Project name to group runs by.
     */
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Each record represent one build run.
     */
    public List<BuildRunWithCounts> getBuildRunWithCountsList() {
        if (buildRunWithCountsList == null) {
            buildRunWithCountsList = new ArrayList<>();
        }

        return buildRunWithCountsList;
    }

    public void setBuildRunWithCountsList(List<BuildRunWithCounts> buildRunWithCountsList) {
        this.buildRunWithCountsList = buildRunWithCountsList;
    }
}
