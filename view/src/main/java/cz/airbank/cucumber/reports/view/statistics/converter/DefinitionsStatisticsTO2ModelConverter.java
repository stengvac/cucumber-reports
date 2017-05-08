package cz.airbank.cucumber.reports.view.statistics.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.view.model.StatisticsDefinitionCounts;
import cz.airbank.cucumber.reports.view.statistics.model.DefinitionsStatisticsModel;

/**
 * Converter from {@link java.util.Map.Entry<String, StatisticsDefinitionCounts>} to {@link DefinitionsStatisticsModel}.
 *
 * @author David Passler
 */
@Component
public class DefinitionsStatisticsTO2ModelConverter implements Converter<Map.Entry<String, StatisticsDefinitionCounts>, DefinitionsStatisticsModel> {

    @Override
    public DefinitionsStatisticsModel convert(Map.Entry<String, StatisticsDefinitionCounts> source) {
        final int featureCount = source.getValue().getFeatureCount();
        final int scenarioCount = source.getValue().getScenarioCount();

        final DefinitionsStatisticsModel definitionsStatisticsModel = new DefinitionsStatisticsModel();
        definitionsStatisticsModel.setModuleName(source.getKey());
        definitionsStatisticsModel.setFeatureCount(featureCount);
        definitionsStatisticsModel.setScenarioCount(scenarioCount);

        return definitionsStatisticsModel;
    }
}
