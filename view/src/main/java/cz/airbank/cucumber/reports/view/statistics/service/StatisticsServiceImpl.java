package cz.airbank.cucumber.reports.view.statistics.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.airbank.cucumber.reports.dao.BuildRunDao;
import cz.airbank.cucumber.reports.dao.entity.DaoFeatureMetadata;
import cz.airbank.cucumber.reports.dao.to.BuildRunWithTestSuitesTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteWithFeaturesMetadataTo;
import cz.airbank.cucumber.reports.view.model.StatisticsDefinitionCounts;
import cz.airbank.cucumber.reports.view.model.StatisticsResultCounts;

/**
 * Implementation for {@link StatisticsService}.
 *
 * @author David Passler
 */
@Service(value = "statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

    private final BuildRunDao buildRunDao;

    @Autowired
    public StatisticsServiceImpl(BuildRunDao buildRunDao) {
        this.buildRunDao = buildRunDao;
    }

    @Override
    public Map<String, StatisticsDefinitionCounts> getDefinitionStatistics() {
        final List<BuildRunWithTestSuitesTo> latestBuilds = buildRunDao.getLatestBuildsPerProject();
        final SortedMap<String, StatisticsDefinitionCounts> allCounts = new TreeMap<>();
        final Set<String> alreadyCountedFeatures = new HashSet<>();

        for (BuildRunWithTestSuitesTo latestBuild : latestBuilds) {
            for (TestSuiteWithFeaturesMetadataTo testSuiteWithFeaturesMetadata : latestBuild.getTestSuiteWithFeaturesMetadataToes()) {
                for (DaoFeatureMetadata metadata : testSuiteWithFeaturesMetadata.getFeatureMetadataList()) {
                    final String uniqueFeatureIdentification = metadata.getModule() + "/" + metadata.getFilename() + "/" + metadata.getGlue();
                    if (!allCounts.containsKey(metadata.getModule())) {
                        allCounts.put(metadata.getModule(), new StatisticsDefinitionCounts());
                    }

                    final StatisticsDefinitionCounts moduleDefinitionsStatisticsTO = allCounts.get(metadata.getModule());

                    if (!alreadyCountedFeatures.add(uniqueFeatureIdentification)) {
                        continue;
                    }

                    moduleDefinitionsStatisticsTO.increaseFeatureCount();
                    moduleDefinitionsStatisticsTO.increaseScenarioCountBy(metadata.getScenarioDefinitionsTotalCount());
                }
            }
        }
        return allCounts;
    }

    @Override
    public Map<String, StatisticsResultCounts> getResultsStatistics() {
        final List<BuildRunWithTestSuitesTo> latestBuilds = buildRunDao.getLatestBuildsPerProject();
        final SortedMap<String, StatisticsResultCounts> allCounts = new TreeMap<>();

        for (BuildRunWithTestSuitesTo latestBuild : latestBuilds) {
            for (TestSuiteWithFeaturesMetadataTo testSuiteExecution : latestBuild.getTestSuiteWithFeaturesMetadataToes()) {
                for (DaoFeatureMetadata metadata : testSuiteExecution.getFeatureMetadataList()) {
                    final String moduleName = metadata.getModule();
                    if (!allCounts.containsKey(moduleName)) {
                        allCounts.put(moduleName, new StatisticsResultCounts());
                    }

                    final StatisticsResultCounts resultsStatisticsTO = allCounts.get(moduleName);

                    if (metadata.passed()) {
                        resultsStatisticsTO.increaseFeaturePassedCount();
                    } else {
                        resultsStatisticsTO.increaseFeatureFailedCount();
                    }

                    resultsStatisticsTO.addScenarioPassedCount(metadata.getScenarioExecutionsPassed());
                    resultsStatisticsTO.addScenarioTotalCount(metadata.getScenarioExecutionsTotal());
                    resultsStatisticsTO.addFeatureDuration(metadata.getTotalExecutionDuration());
                }
            }
        }

        return allCounts;
    }
}
