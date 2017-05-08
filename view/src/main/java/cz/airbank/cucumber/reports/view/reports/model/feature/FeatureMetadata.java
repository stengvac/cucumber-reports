package cz.airbank.cucumber.reports.view.reports.model.feature;

import java.io.Serializable;

/**
 * Basic information about executed feature file.
 * Provide information about feature name, feature location alias module and used glue which specify location of
 * steps implementation.
 *
 * @author Vaclav Stengl
 */
public class FeatureMetadata implements Serializable {

    private static final long serialVersionUID = 1758864306961144533L;

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
     * Was feature execution success?
     */
    public boolean passed() {
        return scenarioExecutionsPassed == scenarioExecutionsTotal;
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
     * Quantity of scenario executions without failure.
     */
    public int getScenarioExecutionsPassed() {
        return scenarioExecutionsPassed;
    }

    public void setScenarioExecutionsPassed(int scenarioExecutionsPassed) {
        this.scenarioExecutionsPassed = scenarioExecutionsPassed;
    }

    /**
     * Total number of executed steps during feature execution inside scenario.
     */
    public int getScenarioStepExecutionsTotal() {
        return scenarioStepExecutionsTotal;
    }

    public void setScenarioStepExecutionsTotal(int scenarioStepExecutionsTotal) {
        this.scenarioStepExecutionsTotal = scenarioStepExecutionsTotal;
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
}

