package cz.airbank.cucumber.reports.dao.entity;

import java.util.ArrayList;
import java.util.List;

import cz.airbank.cucumber.reports.common.model.ScenarioType;

/**
 * Definition entity for Scenario (Background, Scenario, ScenarioOutline).
 *
 * @author David Passler
 */
public class ScenarioDefinition extends TaggedStatement {

    private static final long serialVersionUID = -8108662329036720330L;

    private ScenarioType type;
    private List<StepDefinition> stepDefinitions;
    private List<ScenarioExecution> scenarioExecutions;
    private List<RowTableDefinition> examples;
    private List<HookDefinition> beforeHookDefinitions;
    private List<HookDefinition> afterHookDefinitions;

    /**
     * Scenario type - different types of have set different attributes.
     */
    public ScenarioType getType() {
        return type;
    }

    public void setType(ScenarioType type) {
        this.type = type;
    }

    /**
     * The execution of this scenario definition.
     * Following rules apply for different {@link ScenarioType}.
     *
     * {@link ScenarioType#BACKGROUND} have 0 executions - its executed before each scenario execution so results are placed here
     * {@link ScenarioType#SCENARIO} have 1 execution with all results
     * {@link ScenarioType#SCENARIO_OUTLINE} have 1 up to n executions (depends on example section {@link #getExamples()}).
     * Each execution hold data/results user/created during execution.
     *
     * @return execution associated with this scenario definition according to rules described before.
     */
    public List<ScenarioExecution> getScenarioExecutions() {
        if (scenarioExecutions == null) {
            scenarioExecutions = new ArrayList<>();
        }

        return scenarioExecutions;
    }

    public void setScenarioExecutions(List<ScenarioExecution> scenarioExecutions) {
        this.scenarioExecutions = scenarioExecutions;
    }

    /**
     * Steps present in this scenario.
     */
    public List<StepDefinition> getStepDefinitions() {
        if (stepDefinitions == null) {
            stepDefinitions = new ArrayList<>();
        }

        return stepDefinitions;
    }

    public void setStepDefinitions(List<StepDefinition> stepDefinitions) {
        this.stepDefinitions = stepDefinitions;
    }

    /**
     * Data source for placeholders defined inside scenario outline.
     * Each row provide data for one scenario outline run.
     *
     * @return {@code null} when {@link #type} not {@link ScenarioType#SCENARIO_OUTLINE}
     */
    public List<RowTableDefinition> getExamples() {
        if (examples == null) {
            examples = new ArrayList<>();
        }

        return examples;
    }

    public void setExamples(List<RowTableDefinition> examples) {
        this.examples = examples;
    }

    /**
     * List of executed methods with annotation {@code cucumber.api.Before}.
     */
    public List<HookDefinition> getBeforeHookDefinitions() {
        if (beforeHookDefinitions == null) {
            beforeHookDefinitions = new ArrayList<>();
        }

        return beforeHookDefinitions;
    }

    public void setBeforeHookDefinitions(List<HookDefinition> beforeHookDefinitions) {
        this.beforeHookDefinitions = beforeHookDefinitions;
    }

    /**
     * List of executed methods with annotation {@code cucumber.api.After}.
     */
    public List<HookDefinition> getAfterHookDefinitions() {
        if (afterHookDefinitions == null) {
            afterHookDefinitions = new ArrayList<>();
        }

        return afterHookDefinitions;
    }

    public void setAfterHookDefinitions(List<HookDefinition> afterHookDefinitions) {
        this.afterHookDefinitions = afterHookDefinitions;
    }

    @Override
    public String toString() {
        return "ScenarioDefinition{" +
                "description='" + getDescription() + '\'' +
                ", type=" + type +
                ", stepDefinitions=" + stepDefinitions +
                ", scenarioExecutions=" + scenarioExecutions +
                ", name='" + getName() + '\'' +
                ", tagList=" + getTagList() +
                ", examples=" + examples +
                ", beforeHookDefinitions=" + beforeHookDefinitions +
                ", afterHookDefinitions=" + afterHookDefinitions +
                ", commentList=" + getCommentList() +
                ", range=" + getRange() +
                '}';
    }
}
