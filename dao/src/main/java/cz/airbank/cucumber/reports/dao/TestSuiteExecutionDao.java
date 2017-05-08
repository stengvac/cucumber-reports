package cz.airbank.cucumber.reports.dao;

import java.util.List;

import cz.airbank.cucumber.reports.dao.to.TestSuiteWithFeaturesMetadataTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteReportTo;

/**
 * DAO for operations related to test suite executions.
 *
 * @author Vaclav Stengl
 */
public interface TestSuiteExecutionDao {

    /**
     * Find test suite metadata with id by provided list of test suite execution ids.
     *
     * @param testSuiteExecutionIds to search by
     *
     * @return list of found metadata with ids
     */
    List<TestSuiteMetadataWithIdTo> findTestSuiteMetadataWithId(List<String> testSuiteExecutionIds);

    /**
     * Try to find test suite execution report for given buildId and test suite with provided id.
     *
     * @param testSuiteId of test suite to report
     *
     * @return test suite report or {@code null} if report was not found
     */
    TestSuiteReportTo findTestSuiteReport(String testSuiteId);

    /**
     * For given list of ids return test suite feature metadata and also metadata of features included
     * in those test suites. Test suites can be from different build runs so its needed to map them back to
     * their build run.
     *
     * @param ids to search test suites by
     *
     * @return list of found test suites.
     */
    List<TestSuiteWithFeaturesMetadataTo> findTestSuitesMetadataWithIds(List<String> ids);
}
