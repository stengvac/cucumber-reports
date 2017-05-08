package cz.airbank.cucumber.reports.view.reports.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import cz.airbank.cucumber.reports.view.model.BuildRunWithCounts;
import cz.airbank.cucumber.reports.view.model.ProjectWithRuns;
import cz.airbank.cucumber.reports.view.reports.service.ReportsService;

/**
 * JSF bean which has functionality for reports overview.
 *
 * @author David Passler
 */
@Named
@Scope(org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST)
public class ReportsOverviewBean {

    /**
     * Outcome used in {@code faces-config.xml} to redirect to overview page in reports section.
     */
    public static final String OVERVIEW_OUTCOME = "toReportsOverview";

    static final int BUILD_RUNS_COUNT = 10;

    private final ReportsService reportsService;
    private List<ProjectWithRuns> projectsWithRuns;

    @Autowired
    public ReportsOverviewBean(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    /**
     * Obtain data form service.
     */
    @PostConstruct
    public void init() {
        projectsWithRuns = reportsService.getLatestBuilds(BUILD_RUNS_COUNT);
    }

    /**
     * List with found projects and last {@link #BUILD_RUNS_COUNT} build runs.
     */
    public List<ProjectWithRuns> getProjectsWithRuns() {
        if (projectsWithRuns == null) {
            projectsWithRuns = new ArrayList<>();
        }

        return projectsWithRuns;
    }

    /**
     * Compute ratio of passed tests.
     *
     * @param buildRunWithCounts to compute from
     * @return ratio in range <0, 1>
     */
    public double computeTestsPassedRatio(BuildRunWithCounts buildRunWithCounts) {
        return buildRunWithCounts.getScenarioExecutionsPassed()
               / (double) buildRunWithCounts.getScenarioExecutionsTotal();
    }
}
