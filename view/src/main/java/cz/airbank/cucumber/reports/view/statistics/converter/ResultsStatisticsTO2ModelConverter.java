package cz.airbank.cucumber.reports.view.statistics.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.view.model.StatisticsResultCounts;
import cz.airbank.cucumber.reports.view.statistics.model.ResultsStatisticsModel;

/**
 * Converter from {@link Map<String, cz.airbank.cucumber.reports.view.model.StatisticsResultCounts>} to {@link ResultsStatisticsModel}.
 *
 * @author David Passler
 */
@Component
public class ResultsStatisticsTO2ModelConverter implements Converter<Map.Entry<String, StatisticsResultCounts>, ResultsStatisticsModel> {

    @Override
    public ResultsStatisticsModel convert(Map.Entry<String, StatisticsResultCounts> source) {
        final ResultsStatisticsModel resultsStatisticsModel = new ResultsStatisticsModel();

        resultsStatisticsModel.setModuleName(source.getKey());

        final StatisticsResultCounts resultsStatisticsTO = source.getValue();
        resultsStatisticsModel.setFeaturePassedCount(resultsStatisticsTO.getFeaturePassedCount());
        resultsStatisticsModel.setFeatureFailedCount(resultsStatisticsTO.getFeatureFailedCount());
        resultsStatisticsModel.setScenarioPassedCount(resultsStatisticsTO.getScenarioPassedCount());
        resultsStatisticsModel.setScenarioTotalCount(resultsStatisticsTO.getScenarioTotalCount());

        return resultsStatisticsModel;
    }
}
