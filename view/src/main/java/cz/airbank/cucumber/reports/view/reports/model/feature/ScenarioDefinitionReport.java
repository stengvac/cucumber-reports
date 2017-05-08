package cz.airbank.cucumber.reports.view.reports.model.feature;

import java.util.ArrayList;
import java.util.List;

import cz.airbank.cucumber.reports.common.model.ScenarioType;

/**
 * Enhanced for cucumber reports purposes.
 *
 * @author Vaclav Stengl
 */
public class ScenarioDefinitionReport extends TaggedStatementReport {

    private static final long serialVersionUID = 7072187596328207206L;

    private DataTable examples;
    private List<HookDefinitionReport> beforeHookDefinitionReports;
    private List<StepDefinitionReport> stepDefinitionReports;
    private List<HookDefinitionReport> afterHookDefinitionReports;
    private List<ScenarioRunReport> scenarioRunReports;
    private ScenarioType type;

    /**
     * Some scenario runs failed?
     */
    public boolean isSuccess() {
        for (ScenarioRunReport scenarioRunReport : getScenarioRunReports()) {
            if (!scenarioRunReport.isRunSuccessful()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Return whether {@link #getType()} eq {@link ScenarioType#SCENARIO_OUTLINE}.
     */
    public boolean isScenarioOutline() {
        return ScenarioType.SCENARIO_OUTLINE.equals(type);
    }

    public List<StepDefinitionReport> getStepDefinitionReports() {
        if (stepDefinitionReports == null) {
            stepDefinitionReports = new ArrayList<>();
        }

        return stepDefinitionReports;
    }

    public void setStepDefinitionReports(List<StepDefinitionReport> stepDefinitionReports) {
        this.stepDefinitionReports = stepDefinitionReports;
    }

    /**
     * Scenario runs - contains data about scenario execution.
     * For {@link ScenarioType#SCENARIO_OUTLINE} there usually will be multiple records.
     * For {@link ScenarioType#SCENARIO} there will be exactly one record.
     * For {@link ScenarioType#BACKGROUND} there is 0 records, because background step runs are distributed to other scenarios.
     *
     * @return always not {@code null}
     */
    public List<ScenarioRunReport> getScenarioRunReports() {
        if (scenarioRunReports == null) {
            scenarioRunReports = new ArrayList<>();
        }

        return scenarioRunReports;
    }

    public void setScenarioRunReports(List<ScenarioRunReport> scenarioRunReports) {
        this.scenarioRunReports = scenarioRunReports;
    }

    /**
     * Data table with data for scenario outline runs.
     * One row belong to one run.
     *
     * @return not {@code null} only if {@link #isScenarioOutline()}
     */
    public DataTable getExamples() {
        return examples;
    }

    public void setExamples(DataTable examples) {
        this.examples = examples;
    }

    /**
     * The type of scenario.
     *
     * @return always not {@code null}
     */
    public ScenarioType getType() {
        return type;
    }

    public void setType(ScenarioType type) {
        this.type = type;
    }

    /**
     * Contains before hooks executed with this scenario definition.
     * Results of executions can be found in {@link ScenarioRunReport#getBeforeHookStepResults()}.
     *
     * @return an empty collection when there are no before hooks.
     */
    public List<HookDefinitionReport> getBeforeHookDefinitionReports() {
        if (beforeHookDefinitionReports == null) {
            beforeHookDefinitionReports = new ArrayList<>();
        }

        return beforeHookDefinitionReports;
    }

    public void setBeforeHookDefinitionReports(List<HookDefinitionReport> beforeHookDefinitionReports) {
        this.beforeHookDefinitionReports = beforeHookDefinitionReports;
    }

    /**
     * Contains after hooks executed with this scenario definition.
     * Results of executions can be found in {@link ScenarioRunReport#getAfterHookStepResults()}.
     *
     * @return an empty collection when there are no after hooks.
     */
    public List<HookDefinitionReport> getAfterHookDefinitionReports() {
        if (afterHookDefinitionReports == null) {
            afterHookDefinitionReports = new ArrayList<>();
        }

        return afterHookDefinitionReports;
    }

    public void setAfterHookDefinitionReports(List<HookDefinitionReport> afterHookDefinitionReports) {
        this.afterHookDefinitionReports = afterHookDefinitionReports;
    }
}
