package cz.airbank.cucumber.reports.transport.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Scenario definition contains information shared by multiple scenario runs so this class prevent duplicity.
 *
 * @author Vaclav Stengl
 */
public class ScenarioDefinition extends TagStatement {

    private static final long serialVersionUID = 4686945865783327181L;

    private DataTable examples;
    private List<StepDefinition> stepDefinitionList;
    private List<ScenarioRun> scenarioRunList;
    private ScenarioType type;
    private List<HookDefinition> beforeHooks;
    private List<HookDefinition> afterHooks;

    /**
     * Contains rows with data only inside scenario outline is this field not null
     */
    public DataTable getExamples() {
        return examples;
    }

    public void setExamples(DataTable examples) {
        this.examples = examples;
    }

    /**
     * Step definitions.
     */
    public List<StepDefinition> getStepDefinitionList() {
        if (stepDefinitionList == null) {
            stepDefinitionList = new ArrayList<>();
        }

        return stepDefinitionList;
    }

    public void setStepDefinitionList(List<StepDefinition> stepDefinitionList) {
        this.stepDefinitionList = stepDefinitionList;
    }

    /**
     * Scenario type.
     */
    public ScenarioType getType() {
        return type;
    }

    public void setType(ScenarioType type) {
        this.type = type;
    }

    /**
     * Runs of this scenario definition.
     */
    public List<ScenarioRun> getScenarioRunList() {
        if (scenarioRunList == null) {
            scenarioRunList = new ArrayList<>();
        }

        return scenarioRunList;
    }

    public void setScenarioRunList(List<ScenarioRun> scenarioRunList) {
        this.scenarioRunList = scenarioRunList;
    }

    /**
     * Method declarations executed before scenario/scenario outline execution.
     *
     * Before hook is marked with annotation {@code cucumber.api.java.Before}.
     * It is possible, that each scenario will invoke different set of hooks - see annotation doc for details.
     */
    public List<HookDefinition> getBeforeHooks() {
        if (beforeHooks == null) {
            beforeHooks = new ArrayList<>();
        }

        return beforeHooks;
    }

    public void setBeforeHooks(List<HookDefinition> beforeHooks) {
        this.beforeHooks = beforeHooks;
    }

    /**
     * Method declarations executed after scenario/scenario outline execution.
     *
     * After hook is marked with annotation {@code cucumber.api.java.After}.
     * It is possible, that each scenario will invoke different set of hooks - see annotation doc for details.
     */
    public List<HookDefinition> getAfterHooks() {
        if (afterHooks == null) {
            afterHooks = new ArrayList<>();
        }

        return afterHooks;
    }

    public void setAfterHooks(List<HookDefinition> afterHooks) {
        this.afterHooks = afterHooks;
    }
}
