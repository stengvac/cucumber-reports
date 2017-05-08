package cz.airbank.cucumber.reports.view.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import cz.airbank.cucumber.reports.view.model.StatisticsDefinitionCounts;
import cz.airbank.cucumber.reports.view.model.StatisticsResultCounts;
import cz.airbank.cucumber.reports.view.statistics.service.StatisticsService;

/**
 * Mock implementation of {@link StatisticsService}.
 *
 * @author David Passler
 */
@Service
public class StatisticsServiceMockImpl implements StatisticsService {

    /**
     * How many {@link StatisticsDefinitionCounts} will be created.
     */
    public static final int DEFINITION_PER_MODULE_COUNT_TO_AMOUNT = 3;

    /**
     * How many {@link StatisticsResultCounts} will be created.
     */
    public static final int DEFINITION_PASS_FAIL_PER_MODULE_COUNT_TO_AMOUNT = 4;

    @Override
    public Map<String, StatisticsDefinitionCounts> getDefinitionStatistics() {
        final Map<String, StatisticsDefinitionCounts> definitionsStatisticsTOMap = new HashMap<>();

        for (int i = 1; i <= DEFINITION_PER_MODULE_COUNT_TO_AMOUNT; i++) {
            final StatisticsDefinitionCounts moduleCountTO = new StatisticsDefinitionCounts();

            for (int j = 1; j <= i; j++) {
                moduleCountTO.increaseFeatureCount();
                moduleCountTO.increaseScenarioCountBy(1);
            }

            definitionsStatisticsTOMap.put("module" + i, moduleCountTO);
        }

        return definitionsStatisticsTOMap;
    }

    @Override
    public Map<String, StatisticsResultCounts> getResultsStatistics() {
        final Map<String, StatisticsResultCounts> resultsStatisticsTOMap = new HashMap<>();

        for (int i = 1; i <= DEFINITION_PASS_FAIL_PER_MODULE_COUNT_TO_AMOUNT; i++) {
            final StatisticsResultCounts resultsStatisticsTO = new StatisticsResultCounts();

            for (int j = 1; j <= i; j++) {
                resultsStatisticsTO.increaseFeaturePassedCount();
                resultsStatisticsTO.increaseFeatureFailedCount();

                resultsStatisticsTO.addScenarioPassedCount(1);
                resultsStatisticsTO.addScenarioTotalCount(1);
            }

            resultsStatisticsTOMap.put("module" + i, resultsStatisticsTO);
        }

        return resultsStatisticsTOMap;
    }
}
