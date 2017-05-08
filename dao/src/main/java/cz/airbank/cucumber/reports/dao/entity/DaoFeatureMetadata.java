package cz.airbank.cucumber.reports.dao.entity;

import java.io.Serializable;

/**
 * DB representation of basic information about executed feature file.
 * Provide information about feature name, feature location alias module and used glue which specify location of
 * steps implementation.
 *
 * @author Vaclav Stengl
 */
public class DaoFeatureMetadata implements Serializable {

    private static final long serialVersionUID = -2817944189887599513L;

    private String module;
    private String filename;
    private String glue;

    private int scenarioDefinitionsTotalCount;
    private int scenarioExecutionsTotal;
    private int scenarioExecutionsPassed;

    private int scenarioStepDefinitionsTotalCount;
    private int scenarioStepExecutionsTotal;
    private int scenarioStepExecutionsPassed;

    private int backgroundStepDefinitionsTotalCount;
    private int backgroundStepExecutionsTotal;
    private int backgroundStepExecutionPassed;

    private long totalExecutionDuration;

    /**
     * Module where *.feature file is located.
     */
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    /**
     * Feature file name
     */
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Glue used to locate steps implementation
     */
    public String getGlue() {
        return glue;
    }

    public void setGlue(String glue) {
        this.glue = glue;
    }

    /**
     * Indicate whenever one of test scenarios inside this feature was successful.
     *
     * @return false when there is at least one unsuccessful scenario execution.
     */
    public boolean passed() {
        return scenarioExecutionsTotal == scenarioExecutionsPassed;
    }

    /**
     * Total quantity of executed scenarios.
     */
    public int getScenarioExecutionsTotal() {
        return scenarioExecutionsTotal;
    }

    public void setScenarioExecutionsTotal(int scenarioExecutionsTotal) {
        this.scenarioExecutionsTotal = scenarioExecutionsTotal;
    }

    /**
     * Add scenario runs (test runs) to {@link #getScenarioExecutionsTotal()}.
     *
     * @param toAdd amount to add
     */
    public void addScenarioExecutionsTotal(int toAdd) {
        scenarioExecutionsTotal += toAdd;
    }

    /**
     * Quantity of scenario executions without failure.
     */
    public int getScenarioExecutionsPassed() {
        return scenarioExecutionsPassed;
    }

    public void setScenarioExecutionsPassed(int scenarioExecutionsPassed) {
        this.scenarioExecutionsPassed = scenarioExecutionsPassed;
    }

    /**
     * Increment scenarios passed by 1.
     */
    public void incrementScenarioExecutionsPassed() {
        scenarioExecutionsPassed++;
    }

    /**
     * Total number of executed steps during feature execution.
     *
     * Included: background steps, scenario steps, scenario outline x #rows in example section
     * Excluded: before and after hooks
     */
    public int getScenarioStepExecutionsTotal() {
        return scenarioStepExecutionsTotal;
    }

    public void setScenarioStepExecutionsTotal(int scenarioStepExecutionsTotal) {
        this.scenarioStepExecutionsTotal = scenarioStepExecutionsTotal;
    }

    /**
     * Add steps to {@link #getScenarioStepExecutionsTotal()}.
     *
     * @param toAdd amount to add
     */
    public void addScenarioStepExecutionsTotal(int toAdd) {
        scenarioStepExecutionsTotal += toAdd;
    }

    /**
     * Total quantity of executed steps with status eq {@link cz.airbank.cucumber.reports.common.model.StepStatus#PASSED}.
     */
    public int getScenarioStepExecutionsPassed() {
        return scenarioStepExecutionsPassed;
    }

    public void setScenarioStepExecutionsPassed(int scenarioStepExecutionsPassed) {
        this.scenarioStepExecutionsPassed = scenarioStepExecutionsPassed;
    }

    public void incrementScenarioStepExecutionsPassed() {
        scenarioStepExecutionsPassed++;
    }

    /**
     * The total count of scenario definition in {@code .feature file}.
     * Only {@link cz.airbank.cucumber.reports.common.model.ScenarioType#SCENARIO_OUTLINE}
     * and {@link cz.airbank.cucumber.reports.common.model.ScenarioType#SCENARIO} are included.
     */
    public int getScenarioDefinitionsTotalCount() {
        return scenarioDefinitionsTotalCount;
    }

    public void setScenarioDefinitionsTotalCount(int scenarioDefinitionsTotalCount) {
        this.scenarioDefinitionsTotalCount = scenarioDefinitionsTotalCount;
    }

    public void incrementScenarioDefinitions() {
        scenarioDefinitionsTotalCount++;
    }

    /**
     * Total count of step definitions inside background section.
     *
     * @return 0 if there is no background section or does not contain any step definitions.
     */
    public int getBackgroundStepDefinitionsTotalCount() {
        return backgroundStepDefinitionsTotalCount;
    }

    public void setBackgroundStepDefinitionsTotalCount(int backgroundStepDefinitionsTotalCount) {
        this.backgroundStepDefinitionsTotalCount = backgroundStepDefinitionsTotalCount;
    }

    /**
     * Total count of background steps executions.
     *
     * @return 0 if there are 0 {@link #getBackgroundStepDefinitionsTotalCount()}
     */
    public int getBackgroundStepExecutionsTotal() {
        return backgroundStepExecutionsTotal;
    }

    public void setBackgroundStepExecutionsTotal(int backgroundStepExecutionsTotal) {
        this.backgroundStepExecutionsTotal = backgroundStepExecutionsTotal;
    }

    public void addBackgroundStepExecutionsTotal(int toAdd) {
        backgroundStepExecutionsTotal += toAdd;
    }

    /**
     * The total count of passed background step executions.
     * Logically: this number should be lower than {@link #getBackgroundStepExecutionsTotal()}.
     */
    public int getBackgroundStepExecutionPassed() {
        return backgroundStepExecutionPassed;
    }

    public void setBackgroundStepExecutionPassed(int backgroundStepExecutionPassed) {
        this.backgroundStepExecutionPassed = backgroundStepExecutionPassed;
    }

    public void incrementBackgroundStepExecutionPassed() {
        backgroundStepExecutionPassed++;
    }

    /**
     * Total count of step definition inside scenario/scenario outlines.
     * Excluded: background step definitions, before, after hooks.
     */
    public int getScenarioStepDefinitionsTotalCount() {
        return scenarioStepDefinitionsTotalCount;
    }

    public void setScenarioStepDefinitionsTotalCount(int scenarioStepDefinitionsTotalCount) {
        this.scenarioStepDefinitionsTotalCount = scenarioStepDefinitionsTotalCount;
    }

    public void addScenarioStepDefinitions(int toAdd) {
        scenarioStepDefinitionsTotalCount += toAdd;
    }

    /**
     * The total execution time of whole {@code .feature file} counted as duration of all step results
     * Included: results of background steps, scenario steps, before/after hooks
     */
    public long getTotalExecutionDuration() {
        return totalExecutionDuration;
    }

    public void setTotalExecutionDuration(long totalExecutionDuration) {
        this.totalExecutionDuration = totalExecutionDuration;
    }

    /**
     * Increase total duration by given amount.
     *
     * @param duration to increase by
     */
    public void increaseTotalDuration(long duration) {
        totalExecutionDuration += duration;
    }

    @Override
    public String toString() {
        return "DaoFeatureMetadata{" +
                "module='" + module + '\'' +
                ", filename='" + filename + '\'' +
                ", glue='" + glue + '\'' +
                ", scenarioDefinitionsTotalCount=" + scenarioDefinitionsTotalCount +
                ", scenarioExecutionsTotal=" + scenarioExecutionsTotal +
                ", scenarioExecutionsPassed=" + scenarioExecutionsPassed +
                ", scenarioStepDefinitionsTotalCount=" + scenarioStepDefinitionsTotalCount +
                ", scenarioStepExecutionsTotal=" + scenarioStepExecutionsTotal +
                ", scenarioStepExecutionsPassed=" + scenarioStepExecutionsPassed +
                ", backgroundStepDefinitionsTotalCount=" + backgroundStepDefinitionsTotalCount +
                ", backgroundStepExecutionsTotal=" + backgroundStepExecutionsTotal +
                ", backgroundStepExecutionPassed=" + backgroundStepExecutionPassed +
                ", totalExecutionDuration=" + totalExecutionDuration +
                ", passed=" + passed() +
                '}';
    }
}
