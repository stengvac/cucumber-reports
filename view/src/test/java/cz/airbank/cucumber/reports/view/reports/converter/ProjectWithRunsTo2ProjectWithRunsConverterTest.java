package cz.airbank.cucumber.reports.view.reports.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.dao.to.BuildRunWithTestSuitesTo;
import cz.airbank.cucumber.reports.dao.to.ProjectWithRunsTo;
import cz.airbank.cucumber.reports.view.SampleData;
import cz.airbank.cucumber.reports.view.model.BuildRunWithCounts;
import cz.airbank.cucumber.reports.view.model.ProjectWithRuns;

/**
 * Tests for {@link ProjectWithRunsTo2ProjectWithRunsConverter} impl.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectWithRunsTo2ProjectWithRunsConverterTest {

    @Mock
    private BuildRunWithTestSuitesTo2BuildRunWithCountsMapper buildRunWithCountsMapper;

    @InjectMocks
    private ProjectWithRunsTo2ProjectWithRunsConverter converter = new ProjectWithRunsTo2ProjectWithRunsConverterImpl();

    @Test
    public void testConvert() {
        ProjectWithRunsTo projectWithRunsTo = SampleData.createProjectWithRunsTo();
        List<BuildRunWithTestSuitesTo> buildRunWithTestSuitesTos = projectWithRunsTo.getBuildRunWithTestSuitesTos();
        BuildRunWithCounts expectedBuildRunWithCounts = new BuildRunWithCounts();

        when(buildRunWithCountsMapper.convert(buildRunWithTestSuitesTos.get(0))).thenReturn(expectedBuildRunWithCounts);

        ProjectWithRuns converted = converter.convert(projectWithRunsTo);

        assertEquals(SampleData.PROJECT_NAME, converted.getProjectName());
        assertEquals(1, converted.getBuildRunWithCountsList().size());
        assertSame(expectedBuildRunWithCounts, converted.getBuildRunWithCountsList().get(0));
    }
}