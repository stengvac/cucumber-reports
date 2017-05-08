package cz.airbank.cucumber.reports.dao.converter;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.to.BuildRunWithTestSuitesTo;
import cz.airbank.cucumber.reports.dao.to.ProjectWithRunsTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteWithFeaturesMetadataTo;
import cz.airbank.cucumber.reports.dao.util.SampleData;

/**
 * Tests for {@link ProjectWithRunsToMapperImpl}.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectWithRunsToMapperImplTest {

    @Mock
    private BuildRun2BuildRunWithTestSuitesToMapper testSuitesToConverter;

    @InjectMocks
    private ProjectWithRunsToMapperImpl mapper;

    @Test
    public void testConvert_notNullInput() {
        BuildRun buildRun = SampleData.createBuildRun();
        Map<String, List<BuildRun>> groupedBuilds = Collections.singletonMap(SampleData.PROJECT_NAME,
                                                                             Collections.singletonList(buildRun));

        TestSuiteWithFeaturesMetadataTo featuresMetadataTo = new TestSuiteWithFeaturesMetadataTo();
        featuresMetadataTo.setTestSuiteMetadataWithIdTo(SampleData.createTestSuiteMetadataWithIdTo());
        BuildRunWithTestSuitesTo exceptedBuildRunWithTestSuite = new BuildRunWithTestSuitesTo();
        Map<String, TestSuiteWithFeaturesMetadataTo> testSuiteWithFeaturesMetadataToMap
                = Collections.singletonMap(SampleData.TEST_SUITE_ID, featuresMetadataTo);

        when(testSuitesToConverter.convert(buildRun, testSuiteWithFeaturesMetadataToMap))
                .thenReturn(exceptedBuildRunWithTestSuite);

        List<ProjectWithRunsTo> converted = mapper.convert(testSuiteWithFeaturesMetadataToMap, groupedBuilds);

        assertEquals(1, converted.size());
        ProjectWithRunsTo projectWithRunsTo = converted.get(0);
        assertEquals(SampleData.PROJECT_NAME, projectWithRunsTo.getProjectName());
        List<BuildRunWithTestSuitesTo> buildRuns = projectWithRunsTo.getBuildRunWithTestSuitesTos();
        assertEquals(1, buildRuns.size());
        assertSame(exceptedBuildRunWithTestSuite, buildRuns.get(0));
    }

}