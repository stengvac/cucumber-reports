package cz.airbank.cucumber.reports.collector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Iterables;

import cz.airbank.cucumber.reports.transport.model.Argument;
import cz.airbank.cucumber.reports.transport.model.DataRow;
import cz.airbank.cucumber.reports.transport.model.DataTable;
import cz.airbank.cucumber.reports.transport.model.DescribedStatement;
import cz.airbank.cucumber.reports.transport.model.Embedding;
import cz.airbank.cucumber.reports.transport.model.Feature;
import cz.airbank.cucumber.reports.transport.model.FeatureMetadata;
import cz.airbank.cucumber.reports.transport.model.HookDefinition;
import cz.airbank.cucumber.reports.transport.model.LineRange;
import cz.airbank.cucumber.reports.transport.model.LineStatement;
import cz.airbank.cucumber.reports.transport.model.ScenarioDefinition;
import cz.airbank.cucumber.reports.transport.model.ScenarioRun;
import cz.airbank.cucumber.reports.transport.model.ScenarioType;
import cz.airbank.cucumber.reports.transport.model.Statement;
import cz.airbank.cucumber.reports.transport.model.StepDefinition;
import cz.airbank.cucumber.reports.transport.model.StepResult;
import cz.airbank.cucumber.reports.transport.model.StepStatus;
import cz.airbank.cucumber.reports.transport.model.TagStatement;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.BasicStatement;
import gherkin.formatter.model.Comment;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.ExamplesTableRow;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Range;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Row;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;
import gherkin.formatter.model.Tag;

/**
 * Data collector for one executed feature.
 * Use new instance for each feature because this class is state full.
 *
 * @author Vaclav Stengl
 */
public class UnoCucumberDataCollector implements Reporter, Formatter {

    private static final String SCENARIO_KEYWORD = "Scenario";

    /**
     * Reserved name of first header cell in examples table.
     */
    public static final String DESCRIPTION_COLUMN = "DESCRIPTION";

    /**
     * Cucumber seems to use always / instead of File.separator as directory separator
     */
    public static final char DIRECTORY_SEPARATOR = '/';

    private boolean backgroundSteps;
    private Feature testedFeature;
    private boolean inLifeCycle;
    private int backGroundStepsRemain;
    private int backgroundStepsCount;
    private StepResult latestStepResult;
    private final String glue;

    /**
     * Create new instance.
     *
     * @param glue used to create feature metadata
     */
    public UnoCucumberDataCollector(String glue) {
        this.glue = glue;
        this.testedFeature = new Feature();
    }

    @Override
    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {
        //TODO - needed?
    }

    @Override
    public void uri(String s) {
        //first method called for .feature so its used as signal "new feature tested"

        int startOfFile = s.lastIndexOf(DIRECTORY_SEPARATOR);
        if (startOfFile == -1) {
            //not in package
            testedFeature.setFeatureMetadata(new FeatureMetadata("", s, glue));
        } else {
            String module = s.substring(0, startOfFile);
            String filename = s.substring(startOfFile + 1, s.length());
            testedFeature.setFeatureMetadata(new FeatureMetadata(module, filename, glue));
        }
    }

    @Override
    public void feature(gherkin.formatter.model.Feature feature) {
        //basic info about feature
        setTagStatementAttributes(testedFeature, feature);
    }

    @Override
    public void background(Background background) {
        //this method is called for each scenario run -> store data only once per feature
        //background is not required section inside .feature
        if (testedFeature.getBackground() == null) {
            testedFeature.setBackground(new ScenarioDefinition());
            testedFeature.getBackground().setType(ScenarioType.BACKGROUND);
            setDescribedStatementAttributes(testedFeature.getBackground(), background);

            backgroundSteps = true;
        }

        backGroundStepsRemain = backgroundStepsCount;
    }

    @Override
    public void scenario(gherkin.formatter.model.Scenario scenario) {
        //called after startOfScenarioLifeCycle and provide same info -> useless
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        //marks start of scenario outline - this run will hold step definitions
        ScenarioDefinition scenarioDefinitionOut = new ScenarioDefinition();
        scenarioDefinitionOut.setType(ScenarioType.SCENARIO_OUTLINE);
        testedFeature.getScenarioDefinitionList().add(scenarioDefinitionOut);

        setTagStatementAttributes(scenarioDefinitionOut, scenarioOutline);
    }

    @Override
    public void examples(Examples examples) {
        //called once per outline -> store all
        DataTable table = new DataTable();
        setTagStatementAttributes(table, examples);
        List<DataRow> dataRows = new ArrayList<>();

        validateRows(examples.getRows(), examples.getLine());

        if (DESCRIPTION_COLUMN.equalsIgnoreCase(examples.getRows().get(0).getCells().get(0))) {
            for (ExamplesTableRow row : examples.getRows()) {
                //remove description from values
                List<String> withoutDesc = row.getCells().subList(1, row.getCells().size());
                DataRow dataRow = convertDataRow(row, withoutDesc);
                dataRow.setDescriptionColumn(row.getCells().get(0));
                dataRows.add(dataRow);
            }
        } else {
            for (ExamplesTableRow row : examples.getRows()) {
                dataRows.add(convertDataRow(row, row.getCells()));
            }
        }

        table.setHeaders(dataRows.get(0));
        table.setDataRowList(dataRows.subList(1, dataRows.size()));
        //only scenario outline have examples section
        returnLastScenarioDefinition().setExamples(table);
    }

    @Override
    public void startOfScenarioLifeCycle(gherkin.formatter.model.Scenario scenario) {
        //scenario life cycle begins
        inLifeCycle = true;
        //is scenario run -> add new scenario definition
        if (SCENARIO_KEYWORD.equals(scenario.getKeyword())) {
            ScenarioDefinition sc = new ScenarioDefinition();
            sc.setType(ScenarioType.SCENARIO);
            setTagStatementAttributes(sc, scenario);
            testedFeature.getScenarioDefinitionList().add(sc);
        }

        returnLastScenarioDefinition().getScenarioRunList().add(new ScenarioRun());
    }

    @Override
    public void step(gherkin.formatter.model.Step step) {
        ScenarioDefinition lastScenarioDefinition = returnLastScenarioDefinition();
        //is time to collect background steps? - only steps between first background and result method call are stored -> no duplicates
        StepDefinition stepDefinition = createStepDefinition(step);

        if (backgroundSteps) {
            backgroundStepsCount++;
            testedFeature.getBackground().getStepDefinitionList().add(stepDefinition);

            return;
        }

        if (inLifeCycle && --backGroundStepsRemain < 0 && !ScenarioType.SCENARIO_OUTLINE.equals(lastScenarioDefinition.getType())) {
            //inLifeCycle - part of scenario run, backGroundStepsRemain < 0 - background steps are skipped - duplicity, not outline - duplicity
            lastScenarioDefinition.getStepDefinitionList().add(stepDefinition);
        } else if (!inLifeCycle) {
            //first scenario outline run - step definitions
            lastScenarioDefinition.getStepDefinitionList().add(stepDefinition);
        }
    }

    @Override
    public void endOfScenarioLifeCycle(gherkin.formatter.model.Scenario scenario) {
        inLifeCycle = false;
    }

    @Override
    public void eof() {
    }

    @Override
    public void done() {
        //all features were tested
    }

    @Override
    public void before(Match match, Result result) {
        ScenarioDefinition definition = returnLastScenarioDefinition();
        ScenarioRun lastScenarioRun = Iterables.getLast(definition.getScenarioRunList());

        handleNewHook(definition.getBeforeHooks(), lastScenarioRun.getBeforeHookResults(), result, match);
    }

    @Override
    public void result(Result result) {
        //no more background step definitions
        backgroundSteps = false;

        setResultAttributes(result, latestStepResult);
    }

    @Override
    public void after(Match match, Result result) {
        ScenarioDefinition definition = returnLastScenarioDefinition();
        ScenarioRun lastScenarioRun = Iterables.getLast(definition.getScenarioRunList());

        handleNewHook(definition.getAfterHooks(), lastScenarioRun.getAfterHookResults(), result, match);
    }

    @Override
    public void match(Match match) {
        //method call sequence is step -> match -> result
        //so this method must create step result and set arguments.
        latestStepResult = new StepResult();
        latestStepResult.setArguments(convertArguments(match.getArguments()));

        resolveStepResultList().add(latestStepResult);
    }

    @Override
    public void embedding(String mineType, byte[] bytes) {
        //retrieve last result, which will hold embedding
        latestStepResult.getEmbeddingList().add(new Embedding(bytes, mineType));
    }

    @Override
    public void write(String text) {
        //TODO should i persist this value?
        //not used in reports right now
    }

    @Override
    public void close() {
        //this method is called when last feature file in feature list is executed
        //but there can be other java classes with runner annotation so this method is useless for reporting purposes
    }

    /**
     * Set attributes to provided step result instance.
     *
     * @param result     source of attributes
     * @param stepResult target to set attributes to
     */
    private StepResult setResultAttributes(Result result, StepResult stepResult) {
        //remove possible null
        long duration = result.getDuration() == null ? 0 : result.getDuration();
        latestStepResult = stepResult;
        latestStepResult.setStatus(StepStatus.resolveStatus(result.getStatus()));
        latestStepResult.setDuration(duration);
        latestStepResult.setErrorMessage(result.getErrorMessage());

        return latestStepResult;
    }

    /**
     * Perform conversion between cells.
     *
     * @param row   to convert
     * @param cells containing data - can differ from row cells.
     * @return converted row
     */
    private DataRow convertDataRow(gherkin.formatter.model.Row row, List<String> cells) {
        DataRow dataRow = new DataRow();
        dataRow.setLine(row.getLine());
        dataRow.setCommentList(createCommentList(row.getComments()));
        dataRow.setValueList(cells);

        return dataRow;
    }

    /**
     * From source table copy desired data.
     */
    private List<DataRow> createRowList(List<gherkin.formatter.model.Row> rows) {
        List<DataRow> exampleRowList = new ArrayList<>(rows.size());

        for (Row row : rows) {
            exampleRowList.add(convertDataRow(row, row.getCells()));
        }

        return exampleRowList;
    }

    /**
     * From source copy desired data.
     */
    private List<LineStatement> createCommentList(List<Comment> commentList) {
        List<LineStatement> resultList = new ArrayList<>(commentList.size());

        for (Comment comment : commentList) {
            resultList.add(new LineStatement(comment.getLine(), comment.getValue()));
        }

        return resultList;
    }

    /**
     * Copy desired data
     *
     * @param statement      destination
     * @param basicStatement source
     */
    private void setStatementAttributes(Statement statement, BasicStatement basicStatement) {
        statement.setName(basicStatement.getName());
        statement.setCommentList(createCommentList(basicStatement.getComments()));
        Range range = basicStatement.getLineRange();
        statement.setRange(new LineRange(range.getFirst(), range.getLast()));
    }

    /**
     * Copy desired data
     *
     * @param statement          destination
     * @param describedStatement source
     */
    private void setDescribedStatementAttributes(DescribedStatement statement, gherkin.formatter.model.DescribedStatement describedStatement) {
        setStatementAttributes(statement, describedStatement);
        statement.setDescription(describedStatement.getDescription());
    }

    /**
     * Copy desired data
     *
     * @param statement    destination
     * @param tagStatement source
     */
    private void setTagStatementAttributes(TagStatement statement, gherkin.formatter.model.TagStatement tagStatement) {
        setDescribedStatementAttributes(statement, tagStatement);
        List<LineStatement> resultList = new ArrayList<>(tagStatement.getTags().size());

        for (Tag tag : tagStatement.getTags()) {
            resultList.add(new LineStatement(tag.getLine(), tag.getName()));
        }

        statement.setTagList(resultList);
    }

    /**
     * Create new step definition from step.
     */
    private StepDefinition createStepDefinition(Step step) {
        StepDefinition stepDefinition = new StepDefinition();

        setStatementAttributes(stepDefinition, step);
        stepDefinition.setKeyword(step.getKeyword().trim());
        stepDefinition.setArguments(convertArguments(step.getOutlineArgs()));

        if (step.getRows() != null) {
            validateRows(step.getRows(), step.getLine() - step.getRows().size());

            DataTable dataTable = new DataTable();
            List<DataRow> rows = createRowList(new ArrayList<>(step.getRows()));
            dataTable.setHeaders(rows.get(0));
            dataTable.setDataRowList(rows.subList(1, rows.size()));

            stepDefinition.setStepDataTable(dataTable);
        }

        return stepDefinition;
    }

    /**
     * Return last scenario definition from currently processed feature.
     */
    private ScenarioDefinition returnLastScenarioDefinition() {
        return Iterables.getLast(testedFeature.getScenarioDefinitionList());
    }

    /**
     * Validate data rows - examples and step data tables.
     *
     * @param rows to check their size
     * @param line where are located
     */
    private void validateRows(List<? extends Row> rows, int line) {
        if (rows.size() == 1) {
            throw new IllegalArgumentException("DataTable section in feature "
                    + testedFeature.getFeatureMetadata().getModule()
                    + "/"
                    + testedFeature.getFeatureMetadata().getFilename()
                    + " starting at line "
                    + line
                    + " contains only headers??");
        }
    }

    /**
     * Convert arguments send to method by cucumber framework.
     * Arguments are used in:
     * 1, Before and after hooks.
     * 2, Scenario outline methods - example section can contain placeholder and real value is later on sent in this argument list.
     *
     * @param arguments to convert
     * @return converted arguments
     */
    private List<Argument> convertArguments(List<gherkin.formatter.Argument> arguments) {
        if (null == arguments) {
            return new ArrayList<>();
        }

        return arguments.stream().map(argument -> {
            Argument hookArgument = new Argument();
            String argVal = argument.getVal();
            argVal = argVal.replace("<", "").replace(">", "");
            hookArgument.setArgumentValue(argVal);
            hookArgument.setOffset(argument.getOffset());

            return hookArgument;
        }).collect(Collectors.toList());
    }

    /**
     * Return list, which should contain next step result.
     *
     * @return background list when is new scenario run processed and there is no
     * enough results for background steps.
     */
    private List<StepResult> resolveStepResultList() {
        List<ScenarioRun> scenarioRuns = returnLastScenarioDefinition().getScenarioRunList();
        ScenarioRun lastRun = Iterables.getLast(scenarioRuns);

        if (lastRun.getBackgroundStepResults().size() < backgroundStepsCount) {
            return lastRun.getBackgroundStepResults();
        }

        return lastRun.getScenarioStepResults();
    }

    /**
     * Handle hook definition and result. Add hook definition only, when it is first scenario/scenario outline run,
     * but add hook result every time.
     *
     * @param hookDefinitions collection with hook definitions
     * @param hookResults     of hook definitions in scenario run
     * @param result          the source of hook result data
     * @param match           the source of arguments
     */
    private void handleNewHook(List<HookDefinition> hookDefinitions, List<StepResult> hookResults,
                               Result result, Match match) {
        ScenarioDefinition definition = returnLastScenarioDefinition();

        //add hook definitions only in for first scenario run
        if (1 == definition.getScenarioRunList().size()) {
            HookDefinition hook = new HookDefinition();
            hook.setLocation(match.getLocation());
            hook.setArguments(convertArguments(match.getArguments()));

            hookDefinitions.add(hook);
        }

        //always add result
        latestStepResult = setResultAttributes(result, new StepResult());
        latestStepResult.setArguments(convertArguments(match.getArguments()));

        hookResults.add(latestStepResult);
    }

    /**
     * Feature object with collected data.
     */
    public Feature getTestedFeature() {
        return testedFeature;
    }
}
