package cz.airbank.cucumber.reports.view.service;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.dao.BuildRunDao;
import cz.airbank.cucumber.reports.dao.to.BuildRunWithTestSuitesTo;
import cz.airbank.cucumber.reports.view.SampleData;
import cz.airbank.cucumber.reports.view.model.StatisticsDefinitionCounts;
import cz.airbank.cucumber.reports.view.model.StatisticsResultCounts;
import cz.airbank.cucumber.reports.view.statistics.service.StatisticsServiceImpl;

/**
 * Test for {@link StatisticsServiceImpl}.
 *
 * @author David Passler
 */
@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceImplTest {

    @InjectMocks
    private StatisticsServiceImpl service;

    @Mock
    private BuildRunDao dao;

    @After
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(dao);
    }

    /**
     * Test for {@link StatisticsServiceImpl#getDefinitionStatistics()} when two modules are returned from database.
     */
    @Test
    public void testObtainCountsSuccessful() throws Exception {
        // set up the mocks
        Mockito.when(dao.getLatestBuildsPerProject()).thenReturn(SampleData.createSampleBuildRunsWithTestSuites(true));

        // call the tested method
        final Map<String, StatisticsDefinitionCounts> definitionsPerModule = service.getDefinitionStatistics();

        // check the results
        Assert.assertEquals(2, definitionsPerModule.keySet().size());

        for (Map.Entry<String, StatisticsDefinitionCounts> obtainedModuleCount : definitionsPerModule.entrySet()) {
            Assert.assertEquals(2, obtainedModuleCount.getValue().getFeatureCount());
            Assert.assertEquals(4, obtainedModuleCount.getValue().getScenarioCount());
        }

        // verify correct method calls
        Mockito.verify(dao).getLatestBuildsPerProject();
    }

    /**
     * Test for {@link StatisticsServiceImpl#getDefinitionStatistics()} when no modules are in database.
     */
    @Test
    public void testObtainDefinitionsPerModuleEmpty() {
        // set up the mocks
       // Mockito.when(dao.getLatestBuildsPerProject()).thenReturn(new ArrayList<BuildRun>());

        // call the tested method
        final Map<String, StatisticsDefinitionCounts> obtainedModuleCountMap = service.getDefinitionStatistics();

        // check the results
        Assert.assertEquals(0, obtainedModuleCountMap.size());

        // verify correct method calls
        Mockito.verify(dao).getLatestBuildsPerProject();
    }

    /**
     * Test for {@link StatisticsServiceImpl#getResultsStatistics()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testObtainResultCountsPerModule() {
        // set up the mocks
        final List<BuildRunWithTestSuitesTo> sampleBuildRuns = SampleData.createSampleBuildRunsWithTestSuites(false);
        Mockito.when(dao.getLatestBuildsPerProject()).thenReturn(sampleBuildRuns);

        final Map<String, StatisticsResultCounts> definitionPassFailPerModule = service.getResultsStatistics();

        // check the results
        Assert.assertEquals(2, definitionPassFailPerModule.keySet().size());

        for (int i = 0; i < definitionPassFailPerModule.entrySet().size(); i++) {
            final Map.Entry<String, StatisticsResultCounts>
                obtainedDefinitionPassFailPerModule =
                    (Map.Entry<String, StatisticsResultCounts>) definitionPassFailPerModule.entrySet().toArray()[i];
            final StatisticsResultCounts to = obtainedDefinitionPassFailPerModule.getValue();

            Assert.assertEquals(0, to.getFeaturePassedCount());
            Assert.assertEquals(2, to.getFeatureFailedCount());

            Assert.assertEquals(4, to.getScenarioPassedCount());
            Assert.assertEquals(20, to.getScenarioTotalCount());
        }

        // verify correct method calls
        Mockito.verify(dao).getLatestBuildsPerProject();
    }

    /**
     * Test for {@link StatisticsServiceImpl#getResultsStatistics()} when empty list with builds is returned.
     */
    @Test
    public void testObtainResultCountsPerModuleEmpty() {
        // set up the mocks
       // Mockito.when(dao.getLatestBuildsPerProject()).thenReturn(new ArrayList<BuildRun>());

        // call the tested method
        final Map<String, StatisticsResultCounts> obtainedModuleCountMap = service.getResultsStatistics();

        // check the results
        Assert.assertEquals(0, obtainedModuleCountMap.size());

        // verify correct method calls
        Mockito.verify(dao).getLatestBuildsPerProject();
    }
}