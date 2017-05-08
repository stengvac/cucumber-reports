package cz.airbank.cucumber.reports.transport.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Run of scenario definition.
 *
 * @author Vaclav Stengl
 */
public class ScenarioRun implements Serializable {

    private static final long serialVersionUID = 6195151235236703946L;

    private List<StepResult> beforeHookResults;
    private List<StepResult> backgroundStepResults;
    private List<StepResult> scenarioStepResults;
    private List<StepResult> afterHookResults;

    /**
     * Result of steps in currently executed scenario/scenario outline.
     * Step definitions are placed in {@link ScenarioDefinition#getStepDefinitionList()}.
     * Result is linked with step definition by index in collection.
     *
     * @return this collection should always return same amount of results as is step definitions
     */
    public List<StepResult> getScenarioStepResults() {
        if (scenarioStepResults == null) {
            scenarioStepResults = new ArrayList<>();
        }

        return scenarioStepResults;
    }

    public void setScenarioStepResults(List<StepResult> scenarioStepResults) {
        this.scenarioStepResults = scenarioStepResults;
    }

    /**
     * Step result of steps declared in background section and executed in scope of current {@link ScenarioRun}.
     * Step definitions are placed in {@link Feature#getBackground()} if present.
     * Result is linked with step definition by index in collection.
     *
     * @return empty list if there are no result.
     */
    public List<StepResult> getBackgroundStepResults() {
        if (backgroundStepResults == null) {
            backgroundStepResults = new ArrayList<>();
        }

        return backgroundStepResults;
    }

    public void setBackgroundStepResults(List<StepResult> backgroundStepResults) {
        this.backgroundStepResults = backgroundStepResults;
    }

    /**
     * Results of methods annotated with cucumber.api.java.Before.
     * Associated method definitions are located in {@link ScenarioDefinition#beforeHooks} collection - associated by index.
     *
     * Its possible to invoke multiple hooks for one scenario via different tags.
     */
    public List<StepResult> getBeforeHookResults() {
        if (beforeHookResults == null) {
            beforeHookResults = new ArrayList<>();
        }

        return beforeHookResults;
    }

    public void setBeforeHookResults(List<StepResult> beforeHookResults) {
        this.beforeHookResults = beforeHookResults;
    }

    /**
     * Results of methods annotated with cucumber.api.java.After.
     * Associated method definitions are located in {@link ScenarioDefinition#afterHooks} collection - associated by index.
     *
     * Its possible to invoke multiple hooks for one scenario via different tags.
     */
    public List<StepResult> getAfterHookResults() {
        if (afterHookResults == null) {
            afterHookResults = new ArrayList<>();
        }

        return afterHookResults;
    }

    public void setAfterHookResults(List<StepResult> afterHookResults) {
        this.afterHookResults = afterHookResults;
    }
}
