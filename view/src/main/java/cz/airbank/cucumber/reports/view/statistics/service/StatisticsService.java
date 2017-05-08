package cz.airbank.cucumber.reports.view.statistics.service;

import java.util.Map;

import cz.airbank.cucumber.reports.view.model.StatisticsDefinitionCounts;
import cz.airbank.cucumber.reports.view.model.StatisticsResultCounts;

/**
 * Service which provides statistics.
 *
 * @author David Passler
 */
public interface StatisticsService {

    /**
     * @return map, where the key is module name and value is the counts
     */
    Map<String, StatisticsDefinitionCounts> getDefinitionStatistics();

    /**
     * @return map, where the key is module name and value are the counts of test results
     */
    Map<String, StatisticsResultCounts> getResultsStatistics();

}
