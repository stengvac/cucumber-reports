package cz.airbank.cucumber.reports.api;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.entity.DaoBuildRunMetadata;
import cz.airbank.cucumber.reports.dao.entity.DaoFeatureMetadata;
import cz.airbank.cucumber.reports.dao.entity.DaoTestSuiteMetadata;
import cz.airbank.cucumber.reports.dao.entity.FeatureRun;
import cz.airbank.cucumber.reports.transport.model.BuildRunMetadata;
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
import cz.airbank.cucumber.reports.transport.model.TestSuiteMetadata;

/**
 * Sample data for tests.
 *
 * @author Vaclav Stengl
 */
public class SampleData {

    public static final String ROW_TABLE_HEADER_DESCRIPTION_COL = "DESCRIPTION";
    public static final List<String> ROW_TABLE_HEADERS = Collections.unmodifiableList(Arrays.asList("col1", "col2"));
    public static final String ROW_TABLE_DESCRIPTION_VAL = "descval";
    public static final List<String> ROW_TABLE_DATA_VAL = Collections.unmodifiableList(Arrays.asList("val1", "val2"));
    public static final String STEP_KEYWORD = "@And";
    public static final Long STEP_DURATION = 50L;
    public static final String STEP_ERR_MSG = "err msg";
    public static final String MODULE = "module";
    public static final String FILE_NAME = "file";
    public static final String GLUE = "glue";
    public static final String EMBEDDING_ID = "emb_id";
    public static final String PROJECT_NAME = "bname";
    public static final long SEQUENTIAL_NUMBER = 7;
    public static final String CONTENT_TYPE = "cont";

    public static final String ENVIRONMENT_NAME = "enName";
    public static final String EXECUTED_BY = "jenkins";
    public static final List<String> TAGS = Collections.unmodifiableList(Arrays.asList("tag1", "tag2"));

    public static final Map<String, String> ENV_VARS = Collections.singletonMap("profile", "chrome");
    public static final String TEST_SUITE = "suite";

    public static final int LINE_STATEMENT_LINE = 8;
    public static final String LINE_STATEMENT_VAL = "linStateVal";

    public static final int LINE_RANGE_FIRST = 9;
    public static final int LINE_RANGE_LAST = 18;

    public static final String STATEMENT_NAME = "stateName";
    public static final String STATEMENT_DESCRIPTION = "scdesc";

    public static final String ARGUMENT_PLACEHOLDER = "IDPL";
    public static final String ARGUMENT_REAL_VALUE = "idValue";
    public static final int ARG_OFFSET = 40;

    /**
     * Sample data table
     */
    public static DataTable createDataTable() {
        DataTable dataTable = new DataTable();

        DataRow headerRow = new DataRow();
        headerRow.setValueList(ROW_TABLE_HEADERS);
        headerRow.setDescriptionColumn(ROW_TABLE_HEADER_DESCRIPTION_COL);

        setTaggedStatementData(dataTable);
        dataTable.setHeaders(headerRow);
        dataTable.setDataRowList(Collections.singletonList(createDataRow()));

        return dataTable;
    }

    /**
     * create sample line statement
     */
    public static LineStatement createLineStatement() {
        LineStatement lineStatement = new LineStatement();

        lineStatement.setLine(LINE_STATEMENT_LINE);
        lineStatement.setValue(LINE_STATEMENT_VAL);

        return lineStatement;
    }

    /**
     * create sample line range
     */
    public static LineRange createLineRange() {
        LineRange range = new LineRange();

        range.setFirst(LINE_RANGE_FIRST);
        range.setLast(LINE_RANGE_LAST);

        return range;
    }

    /**
     * set attributes in statement class
     */
    private static void setStatementData(Statement statement) {
        statement.setCommentList(Collections.singletonList(createLineStatement()));
        statement.setName(STATEMENT_NAME);
        statement.setRange(createLineRange());
    }

    /**
     * set attributes of described statement
     */
    private static void setDescribedStatementData(DescribedStatement describedStatement) {
        setStatementData(describedStatement);
        describedStatement.setDescription(STATEMENT_DESCRIPTION);
    }

    /**
     * set attributes of tagged statement
     */
    private static void setTaggedStatementData(TagStatement tagStatement) {
        setDescribedStatementData(tagStatement);

        tagStatement.setTagList(Collections.singletonList(createLineStatement()));
    }

    /**
     * create sample data row
     */
    public static DataRow createDataRow() {
        DataRow dataRow = new DataRow();
        dataRow.setValueList(ROW_TABLE_DATA_VAL);
        dataRow.setDescriptionColumn(ROW_TABLE_DESCRIPTION_VAL);

        return dataRow;
    }

    /**
     * Create sample step definition.
     */
    public static StepDefinition createStepDefinition() {
        StepDefinition stepDefinition = new StepDefinition();

        setStatementData(stepDefinition);
        stepDefinition.setKeyword(STEP_KEYWORD);
        stepDefinition.setStepDataTable(createDataTable());
        stepDefinition.setArguments(Collections.singletonList(createArgument(0, ARGUMENT_PLACEHOLDER)));

        return stepDefinition;
    }

    /**
     * Create new scenario definition sample.
     *
     * @param type of scenario
     * @param runs of scenario
     */
    public static ScenarioDefinition createScenarioDefinition(ScenarioType type, List<ScenarioRun> runs) {
        ScenarioDefinition scenarioDefinition = new ScenarioDefinition();

        setTaggedStatementData(scenarioDefinition);
        scenarioDefinition.setType(type);
        List<StepDefinition> stepDefinitions = Collections.singletonList(createStepDefinition());
        scenarioDefinition.setStepDefinitionList(stepDefinitions);
        scenarioDefinition.setScenarioRunList(runs);
        scenarioDefinition.setExamples(createDataTable());

        return scenarioDefinition;
    }

    /**
     * Create sample step result with values set from class constants.
     */
    public static StepResult createStepResult(StepStatus stepStatus) {
        StepResult result = new StepResult();
        result.setErrorMessage(STEP_ERR_MSG);
        result.setDuration(STEP_DURATION);
        result.setStatus(stepStatus);
        result.setArguments(Collections.singletonList(createArgument(20, ARGUMENT_REAL_VALUE)));

        Embedding embedding = new Embedding();
        embedding.setId(EMBEDDING_ID);
        embedding.setContentType(CONTENT_TYPE);
        embedding.setContent(new byte[1]);
        result.getEmbeddingList().add(embedding);

        return result;
    }

    /**
     * Create sample feature with values set from class constants.
     */
    public static Feature createFeature() {
        Feature feature = new Feature();

        setTaggedStatementData(feature);
        feature.setFeatureMetadata(new FeatureMetadata(MODULE, FILE_NAME, GLUE));
        feature.setBuildRunMetadata(createBuildRunMetadata());

        ScenarioRun run = new ScenarioRun();
        StepResult backgroundResult = createStepResult(StepStatus.PASSED);
        StepResult scenarioResult = createStepResult(StepStatus.FAILED);

        run.setScenarioStepResults(Arrays.asList(backgroundResult, scenarioResult));

        ScenarioDefinition scenario = SampleData.createScenarioDefinition(ScenarioType.SCENARIO, Collections.singletonList(run));
        ScenarioDefinition background = SampleData.createScenarioDefinition(ScenarioType.BACKGROUND, Collections.emptyList());

        HookDefinition beforeHookDefinition = new HookDefinition();
        HookDefinition afterHookDefinition = new HookDefinition();
        scenario.getBeforeHooks().add(beforeHookDefinition);
        scenario.getAfterHooks().add(afterHookDefinition);

        feature.setBackground(background);
        feature.getScenarioDefinitionList().add(scenario);

        return feature;
    }

    /**
     * Build run sample.
     */
    public static BuildRun createBuildRun() {
        BuildRun buildRun = new BuildRun();
        DaoBuildRunMetadata metadata = new DaoBuildRunMetadata();
        metadata.setSequentialNumber(SEQUENTIAL_NUMBER);
        metadata.setProjectName(PROJECT_NAME);
        buildRun.setMetadata(metadata);

        return buildRun;
    }

    /**
     * @return sample build run metadata
     */
    public static BuildRunMetadata createBuildRunMetadata() {
        BuildRunMetadata metadata = new BuildRunMetadata();

        metadata.setBuildAt(LocalDateTime.now());
        metadata.setEnvironmentName(ENVIRONMENT_NAME);
        metadata.setExecutedBy(EXECUTED_BY);
        metadata.setSequentialNumber(SEQUENTIAL_NUMBER);
        metadata.setProjectName(PROJECT_NAME);
        metadata.setTags(TAGS);

        return metadata;
    }

    /**
     * @return sample test suite metadata
     */
    public static TestSuiteMetadata createTestSuiteMetadata() {
        TestSuiteMetadata testSuiteMetadata = new TestSuiteMetadata();

        testSuiteMetadata.setEnvVariables(ENV_VARS);
        testSuiteMetadata.setSequentialNumber(SEQUENTIAL_NUMBER);
        testSuiteMetadata.setTestSuite(TEST_SUITE);

        return testSuiteMetadata;
    }

    /**
     * @return sample dao test suite metadata
     */
    public static DaoTestSuiteMetadata createDaoTestSuiteMetadata() {
        DaoTestSuiteMetadata testSuiteMetadata = new DaoTestSuiteMetadata();

        testSuiteMetadata.setEnvVariables(ENV_VARS);
        testSuiteMetadata.setSequentialNumber(SEQUENTIAL_NUMBER);
        testSuiteMetadata.setTestSuite(TEST_SUITE);

        return testSuiteMetadata;
    }

    /**
     * @return sample scenario run
     */
    public static ScenarioRun createScenarioRun() {
        ScenarioRun scenarioRun = new ScenarioRun();

        scenarioRun.setBackgroundStepResults(Collections.singletonList(createStepResult(StepStatus.PASSED)));
        scenarioRun.setScenarioStepResults(Collections.singletonList(createStepResult(StepStatus.FAILED)));

        return scenarioRun;
    }

    /**
     * @return sample dao build run metadata
     */
    public static DaoBuildRunMetadata createDaoBuildRunMetadata() {
        DaoBuildRunMetadata metadata = new DaoBuildRunMetadata();

        metadata.setBuildAt(LocalDateTime.now());
        metadata.setEnvironmentName(ENVIRONMENT_NAME);
        metadata.setExecutedBy(EXECUTED_BY);
        metadata.setSequentialNumber(SEQUENTIAL_NUMBER);
        metadata.setProjectName(PROJECT_NAME);
        metadata.setTags(TAGS);

        return metadata;
    }

    /**
     * @return sample feature run
     */
    public static FeatureRun createFeatureRun() {
        FeatureRun featureRun = new FeatureRun();

        DaoFeatureMetadata featureMetadata = new DaoFeatureMetadata();
        featureRun.setMetadata(featureMetadata);

        return featureRun;
    }

    /**
     * create sample argument
     */
    private static cz.airbank.cucumber.reports.transport.model.Argument createArgument(int offset, String value) {
        cz.airbank.cucumber.reports.transport.model.Argument argument
                = new cz.airbank.cucumber.reports.transport.model.Argument();

        argument.setArgumentValue(value);
        argument.setOffset(offset);

        return argument;
    }
}
