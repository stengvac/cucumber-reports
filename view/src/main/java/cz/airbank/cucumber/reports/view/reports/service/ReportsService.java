package cz.airbank.cucumber.reports.view.reports.service;

import java.util.List;

import cz.airbank.cucumber.reports.view.model.BuildDetailWithCounts;
import cz.airbank.cucumber.reports.view.model.ModuleBuildCounts;
import cz.airbank.cucumber.reports.view.model.ProjectWithRuns;
import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadataWithId;
import cz.airbank.cucumber.reports.view.reports.service.model.BuildRunReport;
import cz.airbank.cucumber.reports.view.reports.service.model.TestSuiteReport;

/**
 * Service for reports.
 *
 * @author David Passler
 */
public interface ReportsService {

    /**
     * Gets the latest build detail specified by given build name
     *
     * @param buildName the name of the build
     * @return last build
     */
    BuildDetailWithCounts getLatestBuildDetail(String buildName);

    /**
     * Get build detail specified by given build name and number
     *
     * @param buildName   the name of the build
     * @param buildNumber the serial number of the build
     * @return last build
     */
    BuildDetailWithCounts getBuildDetail(String buildName, long buildNumber);

    /**
     * @return list of {@link ModuleBuildCounts} which are associated to module's last build.
     */
    List<ModuleBuildCounts> getModuleBuildCounts();

    /**
     * Basic data to present about build runs.
     *
     * @param buildName        the name to search by
     * @param sequentialNumber to retrieve
     * @return {@code null} if not found
     */
    BuildRunMetadataWithId getBuildRunPresentation(String buildName, long sequentialNumber);

    /**
     * Basic data to present about build runs.
     *
     * @param buildName  the name to search by
     * @param maxRecords to retrieve
     */
    List<BuildRunMetadataWithId> getBuildRunPresentations(String buildName, int maxRecords);

    /**
     * Try to obtain build run report for given build id.
     *
     * @param buildId to search build run by
     * @return found report or {@code null} if not found
     */
    BuildRunReport findBuildRunReportById(String buildId);

    /**
     * Obtain test suite report.
     *
     * @param buildId     the id of build which contains requested test suite
     * @param testSuiteId the id of requested test suite
     * @return test suite report or {@code null} if test suite or build with provided id does not exist
     */
    TestSuiteReport findTestSuiteReport(String buildId, String testSuiteId);

    /**
     * Return latest n build runs for each project in DB.
     *
     * @param buildRunsPerProject max number of project to return per each project
     * @return list with results. Each record represent one project.
     * Each record will contain max number of runs given by param.
     */
    List<ProjectWithRuns> getLatestBuilds(int buildRunsPerProject);
}