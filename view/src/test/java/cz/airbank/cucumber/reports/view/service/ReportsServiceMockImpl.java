package cz.airbank.cucumber.reports.view.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;


import cz.airbank.cucumber.reports.view.model.BuildDetailWithCounts;
import cz.airbank.cucumber.reports.view.model.ProjectWithRuns;
import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadata;
import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadataWithId;
import cz.airbank.cucumber.reports.view.model.ModuleBuildCounts;
import cz.airbank.cucumber.reports.view.reports.service.ReportsService;
import cz.airbank.cucumber.reports.view.reports.service.model.BuildRunReport;
import cz.airbank.cucumber.reports.view.reports.service.model.TestSuiteReport;

/**
 * Mock implementation for {@link ReportsService}.
 *
 * @author David Passler
 */
@Service
public class ReportsServiceMockImpl implements ReportsService {

    public static final int MODULE_BUILD_COUNTS_SIZE = 3;
    public static final int COUNT_MULTIPLIER = 10;

    @Override
    public BuildDetailWithCounts getLatestBuildDetail(String buildName) {
        return getBuildDetail(buildName, 24);
    }

    @Override
    public List<ProjectWithRuns> getLatestBuilds(int buildRunsPerProject) {
        return Collections.emptyList();
    }

    @Override
    public BuildDetailWithCounts getBuildDetail(String buildName, long buildNumber) {
        final BuildDetailWithCounts buildDetailWithCounts = new BuildDetailWithCounts();

        buildDetailWithCounts.setBuildRunMetadata(createBuildRunMetadata("projectName", 24));
        buildDetailWithCounts.setCounts(getModuleBuildCounts());

        return buildDetailWithCounts;
    }

    @Override
    public List<ModuleBuildCounts> getModuleBuildCounts() {
        final List<ModuleBuildCounts> moduleBuildCounts = new ArrayList<>();

        for (int i = 1; i <= MODULE_BUILD_COUNTS_SIZE; i++) {
            final ModuleBuildCounts moduleBuildCount = new ModuleBuildCounts();

            moduleBuildCount.setModuleName("cz.airbank.module" + i);
            moduleBuildCount.setBuildRunMetadata(createBuildRunMetadata("lastBuildInProject" + i, i));
            moduleBuildCount.setFeatureCount(10 + COUNT_MULTIPLIER * i);
            moduleBuildCount.setScenarioCount(20 + COUNT_MULTIPLIER * i);
            moduleBuildCount.setStepCount(40 + COUNT_MULTIPLIER * i);

            moduleBuildCounts.add(moduleBuildCount);
        }

        return moduleBuildCounts;
    }

    @Override
    public List<BuildRunMetadataWithId> getBuildRunPresentations(String buildName, int maxRecords) {
        List<BuildRunMetadataWithId> presentations = new ArrayList<>();

        for (int i = 0; i < maxRecords; i++) {
            presentations.add(getBuildRunPresentation(buildName, i));
        }

        return presentations;
    }

    @Override
    public BuildRunMetadataWithId getBuildRunPresentation(String projectName, long sequentialNumber) {
        BuildRunMetadataWithId presentation = new BuildRunMetadataWithId();
        presentation.setMetadata(createBuildRunMetadata(projectName, sequentialNumber));
        presentation.setId("buildId");

        return presentation;
    }

    @Override
    public BuildRunReport findBuildRunReportById(String buildId) {
        return new BuildRunReport();
    }

    @Override
    public TestSuiteReport findTestSuiteReport(String buildId, String testSuiteId) {
        return new TestSuiteReport();
    }

    /**
     * Create build run metadata.
     *
     * @param projectName to set
     * @param sequentialNumber to set
     */
    private BuildRunMetadata createBuildRunMetadata(String projectName, long sequentialNumber) {
        BuildRunMetadata buildRunMetadata = new BuildRunMetadata();
        buildRunMetadata.setProjectName(projectName);
        buildRunMetadata.setSequentialNumber(sequentialNumber);
        buildRunMetadata.setBuildAt(LocalDateTime.now());
        buildRunMetadata.setPassed(true);

        return buildRunMetadata;
    }
}
