package cz.airbank.cucumber.reports.collector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.SummaryPrinter;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.model.CucumberFeature;
import cz.airbank.cucumber.reports.transport.model.Argument;
import cz.airbank.cucumber.reports.transport.model.DataRow;
import cz.airbank.cucumber.reports.transport.model.DataTable;
import cz.airbank.cucumber.reports.transport.model.Feature;
import cz.airbank.cucumber.reports.transport.model.HookDefinition;
import cz.airbank.cucumber.reports.transport.model.LineStatement;
import cz.airbank.cucumber.reports.transport.model.ScenarioDefinition;
import cz.airbank.cucumber.reports.transport.model.ScenarioRun;
import cz.airbank.cucumber.reports.transport.model.ScenarioType;
import cz.airbank.cucumber.reports.transport.model.StepDefinition;
import cz.airbank.cucumber.reports.transport.model.StepResult;
import cz.airbank.cucumber.reports.transport.model.StepStatus;

/**
 * Tests for {@link UnoCucumberDataCollector} class.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class UnoCucumberDataCollectorTest {

    /**
     * Location of steps implementations.
     */
    private static final String GLUE = "cz.airbank.cucumber.reports.formatter";
    /**
     * Prefix to feature files.
     */
    private static final String FEATURE_PATH_PREFIX = "src/test/resources/" + GLUE + "/";

    //runtime options will search steps definition in package specified as 2nd arg
    private RuntimeOptions runtimeOptions = Mockito.spy(new RuntimeOptions(Arrays.asList("--glue", GLUE)));

    @Mock
    private SummaryPrinter summaryPrinter;

    private UnoCucumberDataCollector formatter;
    private ClassLoader classLoader = getClass().getClassLoader();
    private ResourceLoader resourceLoader = new MultiLoader(getClass().getClassLoader());


    /**
     * Run cucumber .feature
     */
    @Before
    public void setUp() {
        formatter = new UnoCucumberDataCollector(GLUE);
        when(runtimeOptions.formatter(classLoader)).thenReturn(formatter);
        when(runtimeOptions.reporter(classLoader)).thenReturn(formatter);
        //avoid null point
        when(runtimeOptions.summaryPrinter(classLoader)).thenReturn(summaryPrinter);

        doNothing().when(summaryPrinter).print(any(Runtime.class));
    }

    /**
     * Test whenever attribute are set properly for super class of nearly all model objects
     */
    @Test
    public void testTagStatementSetProperly() throws Exception {
        runFeatureFiles("scenario.feature");

        Feature feature = getTestedFeature();

        assertEquals("Hello cucumberrr", feature.getName());
        assertEquals(1, feature.getTagList().size());
        assertEquals("@Yolo", feature.getTagList().get(0).getValue());
        assertEquals(Integer.valueOf(1), feature.getTagList().get(0).getLine());
        assertEquals("My Basic Feature which tests some calculator operations", feature.getDescription());
        assertEquals(1, feature.getScenarioDefinitionList().size());
        assertEquals(1, feature.getScenarioDefinitionList().get(0).getCommentList().size());
        assertEquals("#comment", feature.getScenarioDefinitionList().get(0).getCommentList().get(0).getValue());
        assertEquals(Integer.valueOf(5), feature.getScenarioDefinitionList().get(0).getCommentList().get(0).getLine());
    }

    /**
     * Is scenario tree build properly and attributes set
     */
    @Test
    public void testScenario() throws Exception {
        runFeatureFiles("scenario.feature");

        Feature feature = getTestedFeature();

        assertEquals(1, feature.getScenarioDefinitionList().size());
        ScenarioDefinition lastDefinition = returnLastScenarioDefinition();
        assertEquals(1, lastDefinition.getScenarioRunList().size());
        assertEquals(null, feature.getBackground());
        assertNull(lastDefinition.getExamples());

        ScenarioRun scenarioRun = returnLastScenarioRun(lastDefinition);
        assertEquals(StepStatus.PASSED, scenarioRun.getScenarioStepResults().get(0).getStatus());
        assertEquals(StepStatus.PENDING, scenarioRun.getScenarioStepResults().get(1).getStatus());
        assertNotNull(scenarioRun.getScenarioStepResults().get(1).getErrorMessage());
        assertNotNull(scenarioRun.getScenarioStepResults().get(1).getDuration());
        assertEquals(StepStatus.PASSED, returnScenarioStepResult(scenarioRun, 0).getStatus());
        StepResult pendingResult = returnScenarioStepResult(scenarioRun, 1);
        assertEquals(StepStatus.PENDING, pendingResult.getStatus());
        assertNotNull(pendingResult.getErrorMessage());
        assertNotEquals(0, pendingResult.getDuration());
    }

    /**
     * Run .feature with background and scenario outline.
     * Outline contains step definition only in first run
     * Data rows and results (aggregated from background and scenario) are in 1+ runs
     */
    @Test
    public void testScenarioOutlineWithBackgroundSteps() throws Exception {
        runFeatureFiles("scenarioOutline.feature");

        Feature feature = getTestedFeature();

        assertEquals(1, feature.getScenarioDefinitionList().size());
        //contains step definitions
        ScenarioDefinition scenario = returnLastScenarioDefinition();
        assertEquals(1, scenario.getScenarioRunList().size());
        assertNotNull(scenario.getExamples());
        //example
        DataTable expectedExample = createExceptedDataTable(
                createDataRow(18, Arrays.asList("firstNum", "secNum", "operation")),
                createDataRow(19, Arrays.asList("64", "55", "lcm"))
        );
        assertDataTable(expectedExample, scenario.getExamples());

        assertEquals(2, scenario.getStepDefinitionList().size());
        assertEquals("Given", scenario.getStepDefinitionList().get(0).getKeyword());
        assertEquals("When", scenario.getStepDefinitionList().get(1).getKeyword());

        assertEquals(ScenarioType.SCENARIO_OUTLINE, scenario.getType());

        ScenarioRun scenarioRun = returnLastScenarioRun(scenario);

        //background + scenario results
        assertEquals(2, scenarioRun.getScenarioStepResults().size());
        assertEquals(2, scenarioRun.getBackgroundStepResults().size());
        StepResult passedStepResult = returnScenarioStepResult(scenarioRun, 0);
        assertEquals(StepStatus.PASSED, passedStepResult.getStatus());
        assertNotEquals(0, passedStepResult.getDuration());
        assertNull(passedStepResult.getErrorMessage());
        assertEquals(StepStatus.PASSED, returnScenarioStepResult(scenarioRun, 1).getStatus());
        assertEquals(StepStatus.PASSED, returnBackgroundStepResult(scenarioRun, 0).getStatus());
        assertEquals(StepStatus.PASSED, returnBackgroundStepResult(scenarioRun, 1).getStatus());
    }

    /**
     * Step results are propagated to objects
     */
    @Test
    public void testSkipMissingFail() throws Exception {
        runFeatureFiles("failskipmis.feature");
        Feature feature = getTestedFeature();

        List<ScenarioDefinition> scenarioDefinitionList = feature.getScenarioDefinitionList();
        assertEquals(2, scenarioDefinitionList.size());

        ScenarioDefinition firstScenarioDefinition = scenarioDefinitionList.get(0);
        assertEquals(1, firstScenarioDefinition.getScenarioRunList().size());
        ScenarioRun scenarioRun = firstScenarioDefinition.getScenarioRunList().get(0);
        //results first scenario run
        StepResult undefinedResult = returnScenarioStepResult(scenarioRun, 0);
        assertEquals(StepStatus.UNDEFINED, undefinedResult.getStatus());
        assertEquals(0, undefinedResult.getDuration());
        assertNull(undefinedResult.getErrorMessage());

        StepResult skippedResult = returnScenarioStepResult(scenarioRun, 1);
        assertEquals(StepStatus.SKIPPED, skippedResult.getStatus());
        assertEquals(0, skippedResult.getDuration());
        assertNull(skippedResult.getErrorMessage());

        ScenarioDefinition secondScenarioDefinition = scenarioDefinitionList.get(1);
        assertEquals(1, secondScenarioDefinition.getScenarioRunList().size());
        scenarioRun = secondScenarioDefinition.getScenarioRunList().get(0);
        //results second scenario run
        StepResult failedResult = returnScenarioStepResult(scenarioRun, 0);
        assertEquals(StepStatus.FAILED, failedResult.getStatus());
        assertNotEquals(0, failedResult.getDuration());
        assertNotNull(failedResult.getErrorMessage());

        assertEquals(StepStatus.SKIPPED, returnScenarioStepResult(scenarioRun, 1).getStatus());
    }

    /**
     * Background is created properly.
     */
    @Test
    public void testBackgroundStepsAndFillDataTable() throws Exception {
        runFeatureFiles("scenarioOutline.feature");

        Feature feature = getTestedFeature();

        assertNotNull(feature.getBackground());
        List<StepDefinition> stepDefinitions = feature.getBackground().getStepDefinitionList();
        //first data table inside background section - associated with 1st step
        DataTable firstStepDataTable = stepDefinitions.get(0).getStepDataTable();
        //to assert headers and row values
        DataTable expectedTable = createExceptedDataTable(createDataRow(5, Arrays.asList("id", "firstName")),
                createDataRow(6, Arrays.asList("p1", "Jana")));

        assertDataTable(expectedTable, firstStepDataTable);
        //first line have comment
        List<LineStatement> commentList = firstStepDataTable.getHeaders().getCommentList();
        assertEquals(1, commentList.size());
        //comment is located at line 4
        assertEquals(Integer.valueOf(4), commentList.get(0).getLine());
        assertEquals("# phone number is in format +62 (xxx) xxxx-xxxx", commentList.get(0).getValue());

        //second table in background - associated with 2nd step
        expectedTable = createExceptedDataTable(createDataRow(9, Arrays.asList("id", "secondName")),
                createDataRow(10, Arrays.asList("p2", "Jano")));
        //perform assert of table content
        assertDataTable(expectedTable, stepDefinitions.get(1).getStepDataTable());
    }

    /**
     * Examples with first column named {@link UnoCucumberDataCollector#DESCRIPTION_COLUMN} are striped of this column
     * and values are saved as {@link DataRow#getDescriptionColumn()}.
     */
    @Test
    public void testExamplesWithDescription() throws Exception {
        runFeatureFiles("examplesWithDescription.feature");

        ScenarioDefinition definition = returnLastScenarioDefinition();

        assertNotNull(definition.getExamples());
        List<DataRow> dataRows = definition.getExamples().getDataRowList();

        //one row with header, and second with data -> only one data row
        assertEquals(1, dataRows.size());
        //two columns - DESCRIPTION column was removed
        assertEquals(2, dataRows.get(0).getValueList().size());
        //header
        DataRow header = definition.getExamples().getHeaders();
        assertEquals(Arrays.asList("HEAD1", "HEAD2"), header.getValueList());
        assertEquals("DESCRIPTION", header.getDescriptionColumn());
        //data
        DataRow data = dataRows.get(0);
        assertEquals(Arrays.asList("55", "lcm"), data.getValueList());
        //value moved to dedicated field
        assertEquals("My desc", data.getDescriptionColumn());
    }

    /**
     * Test whenever reporting tool can handle hooks.
     */
    @Test
    public void testHooks_present() throws Exception {
        runFeatureFiles("examplesWithDescription.feature");

        ScenarioDefinition definition = returnLastScenarioDefinition();

        assertEquals(1, definition.getBeforeHooks().size());
        HookDefinition beforeHook = definition.getBeforeHooks().get(0);
        assertEquals("FormatterTestSteps.setUp()", beforeHook.getLocation());
        assertTrue(beforeHook.getArguments().isEmpty());

        assertEquals(1, definition.getAfterHooks().size());
        HookDefinition afterHook = definition.getAfterHooks().get(0);
        assertEquals("FormatterTestSteps.tearDown()", afterHook.getLocation());
        assertTrue(afterHook.getArguments().isEmpty());
    }

    /**
     * Method arguments are extracted and set to TO.
     */
    @Test
    public void testArguments_present() throws Exception {
        runFeatureFiles("scenarioOutline.feature");

        ScenarioDefinition definition = returnLastScenarioDefinition();

        StepDefinition stepDefinitionWithArguments = definition.getStepDefinitionList().get(1);

        //only second step def have arguments
        List<Argument> arguments = stepDefinitionWithArguments.getArguments();

        assertEquals(3, arguments.size());
        Argument operationArg = arguments.get(0);
        assertEquals("operation", operationArg.getArgumentValue());
        assertEquals(21, operationArg.getOffset());
        Argument firstNumArg = arguments.get(1);
        assertEquals("firstNum", firstNumArg.getArgumentValue());
        assertEquals(38, firstNumArg.getOffset());
        Argument secondNumArg = arguments.get(2);
        assertEquals("secNum", secondNumArg.getArgumentValue());
        assertEquals(55, secondNumArg.getOffset());

        ScenarioRun scenarioRun = definition.getScenarioRunList().get(0);
        //only second step result has arg values
        StepResult stepResult = scenarioRun.getScenarioStepResults().get(1);
        assertEquals(3, stepResult.getArguments().size());
        List<String> argumentValues = stepResult.getArguments().stream().map(
                Argument::getArgumentValue
        ).collect(Collectors.toList());
        assertTrue(Arrays.asList("64", "55", "lcm").containsAll(argumentValues));
    }

    /**
     * Number of step arguments may differ in step definition ane step execution.
     * So it is needed to make sure, that argument specified via step annotations are saved.
     */
    @Test
    public void testStepArgumentsFromAnnotations_arePresent() throws Exception {
        runFeatureFiles("stepParameters.feature");

        Feature feature = getTestedFeature();

        ScenarioDefinition scenarioDefinition = feature.getScenarioDefinitionList().get(0);
        StepDefinition stepDefinition = scenarioDefinition.getStepDefinitionList().get(0);

        //in feature file step definition contains 1 argument
        assertEquals(1, stepDefinition.getArguments().size());
        Argument argumentDeclaredInFeatureFile = stepDefinition.getArguments().get(0);
        //it should be PHONE_NUMBER
        assertEquals(38, argumentDeclaredInFeatureFile.getOffset());
        assertEquals("PHONE_NUMBER", argumentDeclaredInFeatureFile.getArgumentValue());

        //but step result contains 2 arguments
        ScenarioRun scenarioRun = scenarioDefinition.getScenarioRunList().get(0);
        StepResult stepResult = scenarioRun.getScenarioStepResults().get(0);
        List<Argument> executionArguments = stepResult.getArguments();
        assertEquals(2, executionArguments.size());
        //first is declared in step annotation
        Argument argumentInAnnotation = executionArguments.get(0);
        assertEquals(0, argumentInAnnotation.getOffset());
        assertEquals("Resident", argumentInAnnotation.getArgumentValue());

        //and the second one is declared inside feature file PHONE_NUMBER value
        Argument executionArgumentInFeature = executionArguments.get(1);
        assertEquals(38, executionArgumentInFeature.getOffset());
        assertEquals("+917#########", executionArgumentInFeature.getArgumentValue());
    }

    /**
     * Run feature given by parameter.
     *
     * @param path to .feature file
     */
    private void runFeatureFiles(String path) throws Exception {
        doReturn(CucumberFeature.load(resourceLoader,
                Collections.singletonList(FEATURE_PATH_PREFIX + path),
                Collections.emptyList())).when(runtimeOptions).cucumberFeatures(resourceLoader);
        new Runtime(resourceLoader, new ResourceLoaderClassFinder(resourceLoader, classLoader), classLoader, runtimeOptions).run();
    }

    /**
     * @param expectedTable expected result
     * @param dataTable     actual datatable
     */
    private void assertDataTable(DataTable expectedTable, DataTable dataTable) {
        assertEquals(expectedTable.getDataRowList().size(), dataTable.getDataRowList().size());


        for (int i = 0; i < expectedTable.getDataRowList().size(); i++) {
            DataRow row = dataTable.getDataRowList().get(i);
            DataRow expectedRow = expectedTable.getDataRowList().get(i);
            assertEquals(expectedRow.getLine(), row.getLine());
            assertTrue(expectedRow.getValueList().equals(row.getValueList()));
        }
    }

    /**
     * Latest tested feature from formatter
     */
    private Feature getTestedFeature() {
        return formatter.getTestedFeature();
    }

    /**
     * From tested feature return last scenario definition
     */
    private ScenarioDefinition returnLastScenarioDefinition() {
        List<ScenarioDefinition> scenarioDefinitions = getTestedFeature().getScenarioDefinitionList();

        return scenarioDefinitions.get(scenarioDefinitions.size() - 1);
    }

    /**
     * From given argument return last scenario run.
     *
     * @param definition source of run
     */
    private ScenarioRun returnLastScenarioRun(ScenarioDefinition definition) {
        List<ScenarioRun> runs = definition.getScenarioRunList();

        return runs.get(runs.size() - 1);
    }

    /**
     * From args create data table
     *
     * @param header of table
     * @param rows   for table
     */
    private DataTable createExceptedDataTable(DataRow header, DataRow rows) {
        DataTable exceptedTable = new DataTable();

        exceptedTable.setHeaders(header);
        exceptedTable.setDataRowList(Collections.singletonList(rows));

        return exceptedTable;
    }

    /**
     * Fromm args create data row
     *
     * @param line where row is located in feature file
     * @param data of row
     */
    private DataRow createDataRow(int line, List<String> data) {
        DataRow dataRow = new DataRow();
        dataRow.setLine(line);
        dataRow.setValueList(data);

        return dataRow;
    }

    /**
     * Return step result from scenario run.
     *
     * @param scenarioRun to extract step result from
     * @param index of step result
     */
    private StepResult returnScenarioStepResult(ScenarioRun scenarioRun, int index) {
        return scenarioRun.getScenarioStepResults().get(index);
    }

    /**
     * Return background step result from scenario run.
     *
     * @param scenarioRun to extract background step result from.
     * @param index of step result
     */
    private StepResult returnBackgroundStepResult(ScenarioRun scenarioRun, int index) {
        return scenarioRun.getScenarioStepResults().get(index);
    }
}