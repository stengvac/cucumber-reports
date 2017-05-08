package cz.airbank.cucumber.reports.view.reports.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.view.model.BuildRunWithCounts;
import cz.airbank.cucumber.reports.view.model.ProjectWithRuns;
import cz.airbank.cucumber.reports.view.reports.service.ReportsService;

/**
 * Test for {@link ReportsOverviewBean}.
 *
 * @author David Passler
 */
@RunWith(MockitoJUnitRunner.class)
public class ReportsOverviewBeanTest {

    @Mock
    private ReportsService reportsService;

    @InjectMocks
    private ReportsOverviewBean bean;

    @Test
    public void testObtainDataFromService_obtained() {
        List<ProjectWithRuns> projectWithRuns = new ArrayList<>();

        when(reportsService.getLatestBuilds(ReportsOverviewBean.BUILD_RUNS_COUNT)).thenReturn(projectWithRuns);

        //service call
        bean.init();

        assertSame(projectWithRuns, bean.getProjectsWithRuns());
    }

    @Test
    public void testComputeTestPassedRatio() {
        BuildRunWithCounts buildRunWithCounts = new BuildRunWithCounts();
        buildRunWithCounts.setScenarioExecutionsTotal(10);
        buildRunWithCounts.setScenarioExecutionsPassed(5);

        double computedRatio = bean.computeTestsPassedRatio(buildRunWithCounts);

        assertEquals(0.5, computedRatio, 0.01);
    }

}