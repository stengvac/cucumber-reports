package cz.airbank.cucumber.reports.dao.converter;

import java.util.List;
import java.util.Map;

import cz.airbank.cucumber.reports.common.converter.BiConverter;
import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.to.ProjectWithRunsTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteWithFeaturesMetadataTo;

/**
 * Interface for mapper, which maps retrieved data about test suites to build runs.
 *
 * @author Vaclav Stengl
 */
public interface ProjectWithRunsToMapper
        extends BiConverter<Map<String, TestSuiteWithFeaturesMetadataTo>, Map<String, List<BuildRun>>, List<ProjectWithRunsTo>> {

    /**
     * 1, Map grouped build runs to {@link List<TestSuiteWithFeaturesMetadataTo>} retrieved from another query result.
     * 2, Transform grouped build runs with data to TO. Each map key -> value entry is transformed to one TO.
     *
     * @param testSuiteWithFeaturesMetadataToMap  the retrieved data from another query mapped by test suite id
     * @param buildRunsGroupedByProjectName       multiple build runs grouped by name of project
     *                                            {@link cz.airbank.cucumber.reports.dao.entity.DaoBuildRunMetadata#getProjectName()}
     * @return list of TO, where each of them contains mapped data and preserve grouping.
     */
    List<ProjectWithRunsTo> convert(Map<String, TestSuiteWithFeaturesMetadataTo> testSuiteWithFeaturesMetadataToMap,
                                    Map<String, List<BuildRun>> buildRunsGroupedByProjectName);
}
