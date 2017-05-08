package cz.airbank.cucumber.reports.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent execution of scenario/scenario outline prescribed in {@code *.feature} file.
 *
 * @author Vaclav Stengl
 */
public class ScenarioExecution implements Serializable {

    private static final long serialVersionUID = 2996972414612710593L;

    private List<StepRun> beforeHookRuns;
    private List<StepRun> backgroundStepRuns;
    private List<StepRun> scenarioStepRuns;
    private List<StepRun> afterHookRuns;

    /**
     * Step runs of this associated {@link ScenarioDefinition#getStepDefinitions()}.
     * One step definition from {@link ScenarioDefinition#getStepDefinitions()} is reflected with one result in this collection
     * with same index.
     */
    public List<StepRun> getScenarioStepRuns() {
        if (scenarioStepRuns == null) {
            scenarioStepRuns = new ArrayList<>();
        }

        return scenarioStepRuns;
    }

    public void setScenarioStepRuns(List<StepRun> scenarioStepRuns) {
        this.scenarioStepRuns = scenarioStepRuns;
    }

    /**
     * Step runs of feature background - step definitions can be found inside {@link FeatureRun#getBackground()} if present.
     *
     * @return empty list if background does not have any steps (cucumber allow empty background)
     * or there is no background in {@code *.feature} file.
     */
    public List<StepRun> getBackgroundStepRuns() {
        if (scenarioStepRuns == null) {
            scenarioStepRuns = new ArrayList<>();
        }

        return backgroundStepRuns;
    }

    public void setBackgroundStepRuns(List<StepRun> backgroundStepRuns) {
        this.backgroundStepRuns = backgroundStepRuns;
    }

    /**
     * Runs of methods annotated with {@code cucumber.api.Before}.
     *
     * Associated hook definitions can be found in {@link ScenarioDefinition#getBeforeHookDefinitions()}.
     */
    public List<StepRun> getBeforeHookRuns() {
        if (beforeHookRuns == null) {
            beforeHookRuns = new ArrayList<>();
        }

        return beforeHookRuns;
    }

    public void setBeforeHookRuns(List<StepRun> beforeHookRuns) {
        this.beforeHookRuns = beforeHookRuns;
    }

    /**
     * Runs of methods annotated with {@code cucumber.api.After}.
     *
     * Associated hook definitions can be found in {@link ScenarioDefinition#getAfterHookDefinitions()}.
     */
    public List<StepRun> getAfterHookRuns() {
        if (afterHookRuns == null) {
            afterHookRuns = new ArrayList<>();
        }

        return afterHookRuns;
    }

    public void setAfterHookRuns(List<StepRun> afterHookRuns) {
        this.afterHookRuns = afterHookRuns;
    }

    @Override
    public String toString() {
        return "ScenarioExecution{" +
                "beforeHookRuns=" + beforeHookRuns +
                ", backgroundStepRuns=" + backgroundStepRuns +
                ", scenarioStepRuns=" + scenarioStepRuns +
                ", afterHookRuns=" + afterHookRuns +
                '}';
    }
}
