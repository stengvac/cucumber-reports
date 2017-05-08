package cz.airbank.cucumber.reports.view.reports.bean;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import cz.airbank.cucumber.reports.common.model.ScenarioType;
import cz.airbank.cucumber.reports.common.model.StepStatus;
import cz.airbank.cucumber.reports.view.SampleData;
import cz.airbank.cucumber.reports.view.reports.model.feature.Argument;
import cz.airbank.cucumber.reports.view.reports.model.feature.ResultReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.StepDefinitionReport;

/**
 * Tests for {@link TextEnhanceBean}.
 *
 * @author Vaclav Stengl
 */
public class TextEnhanceBeanTest {

    private TextEnhanceBean bean = new TextEnhanceBean();

    /**
     * For {@link ScenarioType#SCENARIO_OUTLINE} resolve step name with placeholders.
     */
    @Test
    public void testEnrichStepName_sameNumberOfArgs() {
        ResultReport<StepDefinitionReport> stepResultReport = SampleData.createStepResult(StepStatus.PASSED);

        String resolvedName = bean.resolveStepName(stepResultReport);

        assertNotNull(resolvedName);
        String enhancedStepDefinition = String.format(
                "Start of name <a class=\"data-link placeholder\" "
                + "onclick=\"replaceData(this, '%s', '%s')\">%s</a>nd rest.",
                SampleData.ARGUMENT_PLACEHOLDER,  SampleData.ARGUMENT_REAL_VALUE,  SampleData.ARGUMENT_PLACEHOLDER
        );
        assertEquals(enhancedStepDefinition, resolvedName);
    }

    /**
     * No arguments for method -> no changes in step declaration.
     */
    @Test
    public void testNormalScenarioOnlyReturnStepName() {
        ResultReport<StepDefinitionReport> stepResultReport = new ResultReport<>();
        StepDefinitionReport stepDefinitionReport = new StepDefinitionReport();
        stepDefinitionReport.setStepDefinition(SampleData.STEP_WITH_PLACEHOLDER);
        stepResultReport.setDefinitionReport(stepDefinitionReport);

        String resolvedName = bean.resolveStepName(stepResultReport);

        assertEquals(SampleData.STEP_WITH_PLACEHOLDER, resolvedName);
    }

    /**
     * Step execution have more arguments than step definition.
     * So step definition arguments are filled with records from execution if needed.
     * Placeholder value and real data value are same for argument propagated from execution.
     */
    @Test
    public void testEnrichStepName_differentNumberOfArgs() {
        ResultReport<StepDefinitionReport> stepResultReport = SampleData.createStepResult(StepStatus.PASSED);

        //new value for argument so some changes in results are visible
        String definitionArgValue = "changedValue";
        Argument definitionArgument = SampleData.createDefinitionArgument();
        definitionArgument.setArgumentValue(definitionArgValue);

        stepResultReport.setArguments(Arrays.asList(
                SampleData.createExecutionArgument(),
                definitionArgument

        ));

        String resolvedName = bean.resolveStepName(stepResultReport);

        //first link is propagated from execution so all value are same
        //second link is normal argument with placeholder
        String enhancedStepDefinition = String.format(
                "<a class=\"data-link placeholder\" onclick=\"replaceData(this, '%s', '%s')\">%s</a>f name" +
                        " <a class=\"data-link placeholder\" onclick=\"replaceData(this, '%s', '%s')\">%s</a>nd rest.",
                SampleData.ARGUMENT_REAL_VALUE, SampleData.ARGUMENT_REAL_VALUE, SampleData.ARGUMENT_REAL_VALUE,
                SampleData.ARGUMENT_PLACEHOLDER,  definitionArgValue,  SampleData.ARGUMENT_PLACEHOLDER

        );
        assertEquals(enhancedStepDefinition, resolvedName);
    }
}