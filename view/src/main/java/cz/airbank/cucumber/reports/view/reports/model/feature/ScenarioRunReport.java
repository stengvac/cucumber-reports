package cz.airbank.cucumber.reports.view.reports.model.feature;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.airbank.cucumber.reports.common.model.StepStatus;

/**
 * Class representing scenario run
 *
 * @author Vaclav Stengl
 */
public class ScenarioRunReport implements Serializable {

    private ScenarioDefinitionReport report;
    private int runIndex;
    private List<ResultReport<HookDefinitionReport>> beforeHookStepResults;
    private List<ResultReport<StepDefinitionReport>> backgroundStepResults;
    private List<ResultReport<StepDefinitionReport>> scenarioStepResults;
    private List<ResultReport<HookDefinitionReport>> afterHookStepResults;

    /**
     * Total duration of all present steps
     */
    public long computeTotalDuration() {
        return getScenarioStepResults().stream().mapToLong(ResultReport::getDuration).sum()
                + getBackgroundStepResults().stream().mapToLong(ResultReport::getDuration).sum();
    }

    /**
     * @return count of passed steps
     */
    public int computeStepsPassed() {
        int passed = 0;

        passed += getScenarioStepResults().stream().mapToInt(result -> StepStatus.PASSED.equals(result.getStatus()) ? 1 : 0).sum();
        passed += getBackgroundStepResults().stream().mapToInt(result -> StepStatus.PASSED.equals(result.getStatus()) ? 1 : 0).sum();

        return passed;
    }

    /**
     * This report is part of some scenario definition
     */
    public ScenarioDefinitionReport getReport() {
        return report;
    }

    public void setReport(ScenarioDefinitionReport report) {
        this.report = report;
    }

    /**
     * If all steps passed this run was success
     */
    public boolean isRunSuccessful() {
        return computeTotalStepsCount() == computeStepsPassed();
    }


    /**
     * The name of scenario run.
     *
     * @return description column if is present in associated data row
     * otherwise return original name of scenario/scenario outline.
     */
    public String getName() {
        if (report.isScenarioOutline()) {
            String description = report.getExamples().getDataRowList().get(runIndex).getDescriptionColumn();

            if (description != null) {
                return description;
            }
        }

        return report.getName();
    }

    /**
     * @return unique run index within whole feature report
     */
    public int getRunIndex() {
        return runIndex;
    }

    public void setRunIndex(int runIndex) {
        this.runIndex = runIndex;
    }

    /**
     * Return total count of present step results.
     */
    public int computeTotalStepsCount() {
        return getScenarioStepResults().size() + getBackgroundStepResults().size();
    }

    /**
     * Step results, which belong only to background.
     * Associated {@link StepDefinitionReport} can be found in one of {@link FeatureReport#background}
     *
     * @return never {@code null}
     */
    public List<ResultReport<StepDefinitionReport>> getBackgroundStepResults() {
        if (backgroundStepResults == null) {
            backgroundStepResults = new ArrayList<>();
        }

        return backgroundStepResults;
    }

    public void setBackgroundStepResults(List<ResultReport<StepDefinitionReport>> backgroundStepResults) {
        this.backgroundStepResults = backgroundStepResults;
    }


    /**
     * Step results, which belong to scenario.
     * Associated {@link StepDefinitionReport} can be found in one of {@link ScenarioDefinitionReport#stepDefinitionReports}
     *
     * @return never {@code null}
     */
    public List<ResultReport<StepDefinitionReport>> getScenarioStepResults() {
        if (scenarioStepResults == null) {
            scenarioStepResults = new ArrayList<>();
        }

        return scenarioStepResults;
    }

    public void setScenarioStepResults(List<ResultReport<StepDefinitionReport>> scenarioStepResults) {
        this.scenarioStepResults = scenarioStepResults;
    }

    /**
     * Before hooks executed with this scenario run.
     */
    public List<ResultReport<HookDefinitionReport>> getBeforeHookStepResults() {
        if (beforeHookStepResults == null) {
            beforeHookStepResults = new ArrayList<>();
        }

        return beforeHookStepResults;
    }

    public void setBeforeHookStepResults(List<ResultReport<HookDefinitionReport>> beforeHookStepResults) {
        this.beforeHookStepResults = beforeHookStepResults;
    }

    /**
     * After hooks executed with this scenario run.
     */
    public List<ResultReport<HookDefinitionReport>> getAfterHookStepResults() {
        if (afterHookStepResults == null) {
            afterHookStepResults = new ArrayList<>();
        }

        return afterHookStepResults;
    }

    public void setAfterHookStepResults(List<ResultReport<HookDefinitionReport>> afterHookStepResults) {
        this.afterHookStepResults = afterHookStepResults;
    }
}