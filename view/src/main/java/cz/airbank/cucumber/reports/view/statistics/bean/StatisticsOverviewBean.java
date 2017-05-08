package cz.airbank.cucumber.reports.view.statistics.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cz.airbank.cucumber.reports.view.model.StatisticsDefinitionCounts;
import cz.airbank.cucumber.reports.view.model.StatisticsResultCounts;
import cz.airbank.cucumber.reports.view.statistics.converter.DefinitionsStatisticsTO2ModelConverter;
import cz.airbank.cucumber.reports.view.statistics.converter.ResultsStatisticsTO2ModelConverter;
import cz.airbank.cucumber.reports.view.statistics.model.DefinitionsStatisticsModel;
import cz.airbank.cucumber.reports.view.statistics.model.ResultsStatisticsModel;
import cz.airbank.cucumber.reports.view.statistics.service.StatisticsService;

/**
 * Bean which provides statistics.
 *
 * @author David Passler
 */
@Named
@Scope("request")
@Component
public class StatisticsOverviewBean {

    /**
     * Outcome used for overview page in statistics section.
     */
    public static final String OVERVIEW_OUTCOME = "toStatisticsOverview";

    private final StatisticsService statisticsService;

    /**
     * All definitions per module.
     */
    private List<DefinitionsStatisticsModel> definitionsStatisticsModels;

    /**
     * All definitions counted across all modules.
     */
    private DefinitionsStatisticsModel definitionsStatisticsTotalModel;

    /**
     * All definitions passed/failed count per module.
     */
    private List<ResultsStatisticsModel> resultsStatisticsModels;

    /**
     * All definitions passed/failed count across all modules.
     */
    private ResultsStatisticsModel resultsStatisticsTotalModel;

    /**
     * DefinitionsStatistics converter.
     */
    private DefinitionsStatisticsTO2ModelConverter definitionsStatisticsTO2ModelConverter;

    /**
     * Results statistics converter.
     */
    private ResultsStatisticsTO2ModelConverter resultsStatisticsTO2ModelConverter;

    @Autowired
    public StatisticsOverviewBean(StatisticsService statisticsService, DefinitionsStatisticsTO2ModelConverter definitionsStatisticsTO2ModelConverter,
                                  ResultsStatisticsTO2ModelConverter resultsStatisticsTO2ModelConverter) {
        this.statisticsService = statisticsService;
        this.definitionsStatisticsTO2ModelConverter = definitionsStatisticsTO2ModelConverter;
        this.resultsStatisticsTO2ModelConverter = resultsStatisticsTO2ModelConverter;
    }

    @PostConstruct
    void getData() {
        obtainDefinitionsStatisticsModels();
        obtainResultsStatisticsModels();
    }

    /**
     * Obtains all definitions per module from service.
     */
    private void obtainDefinitionsStatisticsModels() {
        final Map<String, StatisticsDefinitionCounts> definitionsStatisticsMap = statisticsService.getDefinitionStatistics();
        this.definitionsStatisticsModels = new ArrayList<>();
        this.definitionsStatisticsTotalModel = new DefinitionsStatisticsModel();

        for (Map.Entry<String, StatisticsDefinitionCounts> definitionPerModule : definitionsStatisticsMap.entrySet()) {
            final int featureCount = definitionPerModule.getValue().getFeatureCount();
            final int scenarioCount = definitionPerModule.getValue().getScenarioCount();

            definitionsStatisticsTotalModel.addFeatureCount(featureCount);
            definitionsStatisticsTotalModel.addScenarioCount(scenarioCount);

            final DefinitionsStatisticsModel definitionsStatisticsModel = definitionsStatisticsTO2ModelConverter.convert(definitionPerModule);

            this.definitionsStatisticsModels.add(definitionsStatisticsModel);
        }
    }

    /**
     * Gets definitions per module.
     *
     * @return the definitions per module
     */
    public List<DefinitionsStatisticsModel> getDefinitionsStatisticsModels() {
        return definitionsStatisticsModels;
    }

    /**
     * Gets module count total.
     *
     * @return the module count total
     */
    public DefinitionsStatisticsModel getDefinitionsStatisticsTotalModel() {
        return definitionsStatisticsTotalModel;
    }

    /**
     * Obtains all passed/failed definitions counts per module from service.
     */
    private void obtainResultsStatisticsModels() {
        final Map<String, StatisticsResultCounts> resultsStatisticsMap = statisticsService.getResultsStatistics();
        this.resultsStatisticsModels = new ArrayList<>();
        this.resultsStatisticsTotalModel = new ResultsStatisticsModel();

        for (Map.Entry<String, StatisticsResultCounts> resultsStatisticsTOEntry : resultsStatisticsMap.entrySet()) {
            final ResultsStatisticsModel resultsStatisticsModel = resultsStatisticsTO2ModelConverter.convert(resultsStatisticsTOEntry);

            final StatisticsResultCounts statisticsResultCounts = resultsStatisticsTOEntry.getValue();

            resultsStatisticsTotalModel.addFeaturePassedCount(statisticsResultCounts.getFeaturePassedCount());
            resultsStatisticsTotalModel.addFeatureFailedCount(statisticsResultCounts.getFeatureFailedCount());
            resultsStatisticsTotalModel.addScenarioPassedCount(statisticsResultCounts.getScenarioPassedCount());
            resultsStatisticsTotalModel.addScenarioTotalCount(statisticsResultCounts.getScenarioTotalCount());

            this.resultsStatisticsModels.add(resultsStatisticsModel);
        }
    }

    /**
     * Gets module pass fail definition counts per module.
     *
     * @return the module pass fail counts per module
     */
    public List<ResultsStatisticsModel> getResultsStatisticsModels() {
        return resultsStatisticsModels;
    }

    /**
     * Gets module pass fail definition count total.
     *
     * @return the module pass fail count total
     */
    public ResultsStatisticsModel getResultsStatisticsTotalModel() {
        return resultsStatisticsTotalModel;
    }
}
