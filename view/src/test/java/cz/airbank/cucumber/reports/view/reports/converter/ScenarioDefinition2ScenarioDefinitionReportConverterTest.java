package cz.airbank.cucumber.reports.view.reports.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.dao.entity.HookDefinition;
import cz.airbank.cucumber.reports.dao.entity.ScenarioDefinition;
import cz.airbank.cucumber.reports.dao.entity.ScenarioExecution;
import cz.airbank.cucumber.reports.dao.entity.StepDefinition;
import cz.airbank.cucumber.reports.view.SampleData;
import cz.airbank.cucumber.reports.view.reports.model.feature.HookDefinitionReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.DataTable;
import cz.airbank.cucumber.reports.view.reports.model.feature.ScenarioDefinitionReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.ScenarioRunReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.StepDefinitionReport;

/**
 * Tests for {@link ScenarioDefinition2ScenarioDefinitionReportConverter} class.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class ScenarioDefinition2ScenarioDefinitionReportConverterTest {

    @Mock
    private HookDefinition2HookDefinitionReportConverter hookReportConverter;

    @Mock
    private RowTableDefinition2DataTableConverter rowTableConverter;

    @Mock
    private ScenarioExecution2ScenarioRunReportMapper scenarioRunReportConverter;

    @Mock
    private StepDefinition2StepDefinitionReportMapper stepDefinitionReportMapper;

    @Mock
    private LineStatement2LineStatementReportConverter lineStatementReportConverter;

    @InjectMocks
    private ScenarioDefinition2ScenarioDefinitionReportConverter converter
        = new ScenarioDefinition2ScenarioDefinitionReportConverterImpl();

    private ScenarioDefinition scenarioDefinition = SampleData.createScenarioDefinition();

    /**
     * Scenario definition is converted correctly.
     */
    @Test
    public void testConvert() {
        List<StepDefinition> stepDefinitions = scenarioDefinition.getStepDefinitions();
        ScenarioExecution execution = scenarioDefinition.getScenarioExecutions().get(0);
        DataTable expectedExamplesTable = new DataTable();
        StepDefinitionReport expectedStepDefinitionReport = new StepDefinitionReport();
        ScenarioRunReport expectedRunReport = new ScenarioRunReport();

        when(scenarioRunReportConverter.convert(execution)).thenReturn(expectedRunReport);
        when(rowTableConverter.listToOne(scenarioDefinition.getExamples())).thenReturn(expectedExamplesTable);
        when(stepDefinitionReportMapper
                .convert(stepDefinitions.get(0)))
                .thenReturn(expectedStepDefinitionReport);

        ScenarioDefinitionReport report = converter.convert(scenarioDefinition);

        assertNotNull(report);
        assertEquals(SampleData.SCENARIO_DESCRIPTION, report.getDescription());
        assertEquals(SampleData.SCENARIO_NAME, report.getName());
        assertEquals(SampleData.SCENARIO_TYPE, report.getType());
        assertSame(expectedExamplesTable, report.getExamples());
        assertNotNull(report.getScenarioRunReports());
        assertEquals(1, report.getScenarioRunReports().size());
        //run converted correctly
        assertSame(expectedRunReport, report.getScenarioRunReports().get(0));
        //step definition converted correctly
        assertEquals(1, report.getStepDefinitionReports().size());
        StepDefinitionReport stepDefinition = report.getStepDefinitionReports().get(0);
        assertSame(expectedStepDefinitionReport, stepDefinition);
    }

    /**
     * Hooks converted.
     */
    @Test
    public void testConvert_hooks() {
        HookDefinition beforeHook = new HookDefinition();
        HookDefinition afterHook = new HookDefinition();

        scenarioDefinition.getBeforeHookDefinitions().add(beforeHook);
        scenarioDefinition.getAfterHookDefinitions().add(afterHook);

        HookDefinitionReport beforeHookDefinitionReport = new HookDefinitionReport();
        HookDefinitionReport afterHookDefinitionReport = new HookDefinitionReport();

        when(hookReportConverter.convert(beforeHook)).thenReturn(beforeHookDefinitionReport);
        when(hookReportConverter.convert(afterHook)).thenReturn(afterHookDefinitionReport);

        ScenarioDefinitionReport report = converter.convert(scenarioDefinition);

        List<HookDefinitionReport> beforeHooks = report.getBeforeHookDefinitionReports();
        assertEquals(1, beforeHooks.size());
        assertSame(beforeHookDefinitionReport, beforeHooks.get(0));

        List<HookDefinitionReport> afterHooks = report.getAfterHookDefinitionReports();
        assertEquals(1, afterHooks.size());
        assertSame(afterHookDefinitionReport, afterHooks.get(0));
    }
}