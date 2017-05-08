package cz.airbank.cucumber.reports.dao;

import cz.airbank.cucumber.reports.dao.to.FeatureReportTo;

/**
 * DAO for operations related to Feature files/executions.
 *
 * @author Vaclav Stengl
 */
public interface FeatureRunDao {

    /**
     * Find feature report.
     *
     * @param buildId the id of build, which contains feature run - obtain only build run metadata
     * @param testSuiteId to search test suite by. And in this test suite try to find requested feature run
     * @param featureIndex of feature within test suite.
     * @return feature run or
     *         {@literal null} when not found
     */
    FeatureReportTo findFeatureReportByIds(String buildId, String testSuiteId, int featureIndex);

}
