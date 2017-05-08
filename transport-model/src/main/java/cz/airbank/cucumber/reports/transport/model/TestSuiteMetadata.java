package cz.airbank.cucumber.reports.transport.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests suite execution metadata, which will contain mainly information about
 * environment variables used to configure test environment.
 *
 * @author Vaclav Stengl
 */
public class TestSuiteMetadata implements Serializable {

    private static final long serialVersionUID = -3245867289699536090L;

    private String testSuite;
    private long sequentialNumber;
    private Map<String, String> envVariables;

    /**
     * The string id/name of test suite.
     * When multiple test suites are executed in one build run, combination of {@link #testSuite} and {@link #sequentialNumber} will serve as
     * identifier of test suite (its possible to execute one set of *.feature files with different environment variables).
     *
     * @return always should be not {@code null}
     */
    public String getTestSuite() {
        return testSuite;
    }

    public void setTestSuite(String testSuite) {
        this.testSuite = testSuite;
    }

    /**
     * The sequential number of suite - this number equal to seq number of sub task/sub job which suite is part of.
     * When multiple test suites are executed in one build run, combination of {@link #testSuite} and {@link #sequentialNumber} will serve as
     * identifier of test suite (its possible to execute one set of *.feature files with different environment variables).
     */
    public long getSequentialNumber() {
        return sequentialNumber;
    }

    public void setSequentialNumber(long sequentialNumber) {
        this.sequentialNumber = sequentialNumber;
    }

    /**
     * Environment variables set for execution of this test suite.
     * So later on can testers/developers find out how to reproduce this environment if needed.
     *
     * @return always not {@code null}
     */
    public Map<String, String> getEnvVariables() {
        if (envVariables == null) {
            envVariables = new HashMap<>();
        }

        return envVariables;
    }

    public void setEnvVariables(Map<String, String> envVariables) {
        this.envVariables = envVariables;
    }
}
