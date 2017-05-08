package cz.airbank.cucumber.reports.view.reports.service;

import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureReport;

/**
 * Service for feature reporting
 *
 * @author Vaclav Stengl
 */
public interface FeatureReportService {

    /**
     * Try to find feature report for given args.
     *
     * @param buildId to search build run data by
     * @param testSuiteId to search test suite data by
     * @param featureRunId the id of feature in test suite context
     */
    FeatureReport getFeatureReportData(String buildId, String testSuiteId, int featureRunId);
}
