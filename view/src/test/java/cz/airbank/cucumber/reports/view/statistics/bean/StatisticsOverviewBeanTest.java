package cz.airbank.cucumber.reports.view.statistics.bean;

import static cz.airbank.cucumber.reports.view.service.StatisticsServiceMockImpl.DEFINITION_PASS_FAIL_PER_MODULE_COUNT_TO_AMOUNT;
import static cz.airbank.cucumber.reports.view.service.StatisticsServiceMockImpl.DEFINITION_PER_MODULE_COUNT_TO_AMOUNT;
import static org.junit.Assert.assertEquals;

import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.airbank.cucumber.reports.view.config.AbstractSpringConfigTest;
import cz.airbank.cucumber.reports.view.service.StatisticsServiceMockImpl;
import cz.airbank.cucumber.reports.view.statistics.converter.DefinitionsStatisticsTO2ModelConverter;
import cz.airbank.cucumber.reports.view.statistics.converter.ResultsStatisticsTO2ModelConverter;
import cz.airbank.cucumber.reports.view.statistics.model.DefinitionsStatisticsModel;
import cz.airbank.cucumber.reports.view.statistics.model.ResultsStatisticsModel;
import cz.airbank.cucumber.reports.view.statistics.service.StatisticsService;

/**
 * Test for {@link StatisticsOverviewBean}.
 *
 * @author David Passler
 */
public class StatisticsOverviewBeanTest extends AbstractSpringConfigTest {

    private StatisticsOverviewBean bean;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private DefinitionsStatisticsTO2ModelConverter definitionsStatisticsTO2ModelConverter;

    @Autowired
    private ResultsStatisticsTO2ModelConverter resultsStatisticsTO2ModelConverter;

    @Before
    public void setUp() {
        bean = new StatisticsOverviewBean(statisticsService, definitionsStatisticsTO2ModelConverter, resultsStatisticsTO2ModelConverter);
        bean.getData();
    }

    /**
     * Test for {@link StatisticsOverviewBean#getDefinitionsStatisticsModels()}.
     */
    @Test
    public void testGetDefinitionsPerModule() {
        final List<DefinitionsStatisticsModel> definitionsPerModule = bean.getDefinitionsStatisticsModels();

        assertEquals(DEFINITION_PER_MODULE_COUNT_TO_AMOUNT, definitionsPerModule.size());

        definitionsPerModule.sort(Comparator.comparing(DefinitionsStatisticsModel::getModuleName));

        for (int i = 1; i <= DEFINITION_PER_MODULE_COUNT_TO_AMOUNT; i++) {
            final DefinitionsStatisticsModel definitionsStatisticsModel = definitionsPerModule.get(i - 1);

            assertEquals(i, definitionsStatisticsModel.getFeatureCount());
            assertEquals(i, definitionsStatisticsModel.getScenarioCount());
        }
    }

    /**
     * Test for {@link StatisticsOverviewBean#getDefinitionsStatisticsTotalModel()}.
     */
    @Test
    public void testGetDefinitionsStatisticsTotalModel() {
        final DefinitionsStatisticsModel definitionsStatisticsTotalModel = bean.getDefinitionsStatisticsTotalModel();

        int expectedFeatureScenarioCount = 0;

        for (int i = 1; i <= DEFINITION_PER_MODULE_COUNT_TO_AMOUNT; i++) {
            expectedFeatureScenarioCount += i;
        }

        assertEquals(expectedFeatureScenarioCount, definitionsStatisticsTotalModel.getFeatureCount());
        assertEquals(expectedFeatureScenarioCount, definitionsStatisticsTotalModel.getScenarioCount());
    }

    /**
     * Test for {@link StatisticsOverviewBean#getResultsStatisticsModels()}.
     */
    @Test
    public void testGetModulePassFailCountsPerDefinitionPerModule_successful() {
        final List<ResultsStatisticsModel> modulePassFailCountsPerDefinitionPerModule = bean.getResultsStatisticsModels();

        assertEquals(StatisticsServiceMockImpl.DEFINITION_PASS_FAIL_PER_MODULE_COUNT_TO_AMOUNT, modulePassFailCountsPerDefinitionPerModule.size());

        modulePassFailCountsPerDefinitionPerModule.sort(Comparator.comparing(ResultsStatisticsModel::getModuleName));

        for (int i = 1; i <= StatisticsServiceMockImpl.DEFINITION_PASS_FAIL_PER_MODULE_COUNT_TO_AMOUNT; i++) {
            final ResultsStatisticsModel DefinitionsStatisticsModel = modulePassFailCountsPerDefinitionPerModule.get(i - 1);

            assertEquals(i, DefinitionsStatisticsModel.getFeaturePassedCount());
            assertEquals(i, DefinitionsStatisticsModel.getFeatureFailedCount());

            assertEquals(i, DefinitionsStatisticsModel.getScenarioPassedCount());
            assertEquals(i, DefinitionsStatisticsModel.getScenarioTotalCount());
        }
    }

    /**
     * Test for {@link StatisticsOverviewBean#getResultsStatisticsTotalModel()}
     */
    @Test
    public void testGetResultsStatisticsTotalModels() {
        final ResultsStatisticsModel resultsStatisticsTotalModel = bean.getResultsStatisticsTotalModel();

        int expectedCount = 0;

        for (int i = 1; i <= DEFINITION_PASS_FAIL_PER_MODULE_COUNT_TO_AMOUNT; i++) {
            expectedCount += i;
        }

        assertEquals(expectedCount, resultsStatisticsTotalModel.getFeaturePassedCount());
        assertEquals(expectedCount, resultsStatisticsTotalModel.getFeatureFailedCount());

        assertEquals(expectedCount, resultsStatisticsTotalModel.getScenarioPassedCount());
        assertEquals(expectedCount, resultsStatisticsTotalModel.getScenarioTotalCount());
    }

}