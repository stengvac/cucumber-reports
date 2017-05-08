package cz.airbank.cucumber.reports.dao.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.to.BuildRunWithTestSuitesTo;
import cz.airbank.cucumber.reports.dao.to.ProjectWithRunsTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteWithFeaturesMetadataTo;

/**
 * Implementation of {@link ProjectWithRunsToMapper}
 *
 * @author Vaclav Stengl
 */
@Component
public class ProjectWithRunsToMapperImpl implements ProjectWithRunsToMapper {

    private final BuildRun2BuildRunWithTestSuitesToMapper testSuitesToConverter;

    @Autowired
    public ProjectWithRunsToMapperImpl(BuildRun2BuildRunWithTestSuitesToMapper testSuitesToConverter) {
        this.testSuitesToConverter = testSuitesToConverter;
    }

    @Override
    public List<ProjectWithRunsTo> convert(Map<String, TestSuiteWithFeaturesMetadataTo> testSuiteWithFeaturesMetadataToMap,
                                           Map<String, List<BuildRun>> groupedBuildRuns) {
        //perform mapping for grouped build runs
        return groupedBuildRuns.entrySet().stream().map(projectNameAndRuns -> {
            ProjectWithRunsTo projectWithRunsTo = new ProjectWithRunsTo();

            List<BuildRunWithTestSuitesTo> buildRunsWithMappedTestSuites = projectNameAndRuns.getValue().stream().map(
                buildRun -> testSuitesToConverter.convert(buildRun, testSuiteWithFeaturesMetadataToMap)
            ).collect(Collectors.toList());

            projectWithRunsTo.setBuildRunWithTestSuitesTos(buildRunsWithMappedTestSuites);
            projectWithRunsTo.setProjectName(projectNameAndRuns.getKey());

            return projectWithRunsTo;
        }).collect(Collectors.toList());
    }
}
