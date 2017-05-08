package cz.airbank.cucumber.reports.view;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cz.airbank.cucumber.reports.common.model.ScenarioType;
import cz.airbank.cucumber.reports.common.model.StepStatus;
import cz.airbank.cucumber.reports.dao.entity.Argument;
import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.entity.DaoBuildRunMetadata;
import cz.airbank.cucumber.reports.dao.entity.DaoFeatureMetadata;
import cz.airbank.cucumber.reports.dao.entity.DaoTestSuiteMetadata;
import cz.airbank.cucumber.reports.dao.entity.FeatureRun;
import cz.airbank.cucumber.reports.dao.entity.HookDefinition;
import cz.airbank.cucumber.reports.dao.entity.RowData;
import cz.airbank.cucumber.reports.dao.entity.RowTableDefinition;
import cz.airbank.cucumber.reports.dao.entity.ScenarioDefinition;
import cz.airbank.cucumber.reports.dao.entity.ScenarioExecution;
import cz.airbank.cucumber.reports.dao.entity.StepDefinition;
import cz.airbank.cucumber.reports.dao.entity.StepRun;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.to.BuildRunMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.BuildRunWithTestSuitesTo;
import cz.airbank.cucumber.reports.dao.to.FeatureReportTo;
import cz.airbank.cucumber.reports.dao.to.ProjectWithRunsTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteWithFeaturesMetadataTo;
import cz.airbank.cucumber.reports.view.model.TestSuiteWithFeaturesMetadata;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadata;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadataWithId;
import cz.airbank.cucumber.reports.view.reports.model.feature.HookDefinitionReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.ResultReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.ScenarioDefinitionReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.ScenarioRunReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.StepDefinitionReport;
import cz.airbank.cucumber.reports.view.reports.model.testsuite.TestSuiteMetadata;
import cz.airbank.cucumber.reports.view.reports.model.testsuite.TestSuiteMetadataWithId;

/**
 * Utils for creating sample {@link BuildRun} for test purposes.
 *
 * @author David Passler
 */
public class SampleData {

    public static final String PROJECT_NAME = "pname";

    public static final String ID = "id";
    public static final String SCENARIO_DESCRIPTION = "scdesc";
    public static final String SCENARIO_NAME = "scname";
    public static final ScenarioType SCENARIO_TYPE = ScenarioType.SCENARIO_OUTLINE;

    public static final String ROW_TABLE_DESCRIPTION = "rtdesc";
    public static final String ROW_TABLE_HEADER_DESCRIPTION_COL = "DESCRIPTION";
    public static final List<String> ROW_TABLE_HEADERS = Collections.unmodifiableList(Arrays.asList("col1", "col2"));
    public static final String ROW_TABLE_DESCRIPTION_VAL = "descval";
    public static final List<String> ROW_TABLE_DATA_VAL = Collections.unmodifiableList(Arrays.asList("val1", "val2"));

    public static final String STEP_KEYWORD = "@And";
    public static final String STEP_NAME = "Carcoolka step";
    public static final long STEP_DURATION = 50;
    public static final String STEP_EMBEDDING_ID = "err img";
    public static final String STEP_ERR_MSG = "err msg";

    public static final String MODULE = "module";
    public static final String FILE_NAME = "file";
    public static final String GLUE = "glue";

    public static final String FEATURE_RUN_DESC = "frdesc";
    public static final String FEATURE_RUN_NAME = "frname";
    public static final String LOCATION = "loc";
    public static final StepRun STEP_RUN = new StepRun();
    public static final Argument ARGUMENT = new Argument();
    public static final StepStatus STEP_STATUS = StepStatus.PASSED;

    public static final String DAO_TESTSUITE_METADATA_SUITE = "suite";
    public static final long DAO_TESTSUITE_METADATA_SEQ_NUMBER = 40;
    public static final boolean PASSED = true;

    public static final int FEATURE_METADATA_SCENARIO_EXECUTIONS_TOTAL = 2;
    public static final int FEATURE_METADATA_SCENARIO_EXECUTIONS_PASSED = 1;
    public static final int FEATURE_METADATA_STEPS_TOTAL = 4;
    public static final int FEATURE_METADATA_STEPS_PASSED = 3;

    public static final int HOOK_ARGUMENT_OFFSET = 5;
    public static final String HOOK_ARGUMENT_VALUE = "hookArgVal";

    public static final String ARGUMENT_PLACEHOLDER = "\"ID\"";
    public static final String ARGUMENT_REAL_VALUE = "idValue";
    public static final String STEP_WITH_PLACEHOLDER = "Start of name " + ARGUMENT_PLACEHOLDER + " and rest.";
    public static final int ARG_OFFSET = STEP_WITH_PLACEHOLDER.indexOf(ARGUMENT_PLACEHOLDER);

    public static List<BuildRun> createSampleBuildRuns(int numberOfBuildRuns, int[] featureCount) {
        final List<BuildRun> buildRuns = new ArrayList<>();

        for (int i = 0; i < numberOfBuildRuns; i++) {
            final BuildRun buildRun = new BuildRun();

            final DaoBuildRunMetadata metadata = new DaoBuildRunMetadata();
            metadata.setProjectName("project" + i);
            buildRun.setMetadata(metadata);

            final TestSuiteExecution testSuiteExecution = new TestSuiteExecution();
            final List<FeatureRun> featureRuns = new ArrayList<>();

            for (int j = 0; j < featureCount[i]; j++) {
                final FeatureRun featureRun = SampleData.createFeatureRun(MODULE + i, FILE_NAME + i, GLUE);
                featureRun.getMetadata().setScenarioDefinitionsTotalCount(12);
                featureRun.getMetadata().setScenarioStepDefinitionsTotalCount(12);

                featureRuns.add(featureRun);
            }

            testSuiteExecution.setFeatureRuns(featureRuns);

            buildRun.setTestSuites(Collections.singletonList(testSuiteExecution));

            buildRuns.add(buildRun);
        }

        return buildRuns;
    }

    /**
     * @return sample build runs
     */
    public static List<BuildRunWithTestSuitesTo> createSampleBuildRunsWithTestSuites(boolean includeDuplicate) {
        // prepare the sample data
        final BuildRunWithTestSuitesTo buildRunWithTestSuitesTo = new BuildRunWithTestSuitesTo();

        final BuildRunMetadataWithIdTo buildRunMetadataWithIdTo = new BuildRunMetadataWithIdTo();

        final LocalDateTime startDate1 = LocalDateTime.of(2016, Month.AUGUST, 10, 11, 24, 33);
        buildRunMetadataWithIdTo.setMetadata(createBuildRunMetadata("buildName1", startDate1, 24L));

        buildRunWithTestSuitesTo.setBuildRunMetadataWithIdTo(buildRunMetadataWithIdTo);
        buildRunWithTestSuitesTo.setTestSuiteWithFeaturesMetadataToes(new ArrayList<>());

        final TestSuiteWithFeaturesMetadataTo testSuiteWithFeaturesMetadataTo = createTestSuiteWithFeaturesMetadataTo();

        final DaoFeatureMetadata daoFeatureMetadata1 = createDaoFeatureMetadata(MODULE, FILE_NAME + "aaa", GLUE);
        daoFeatureMetadata1.setScenarioDefinitionsTotalCount(2);
        daoFeatureMetadata1.setScenarioExecutionsPassed(2);
        daoFeatureMetadata1.setScenarioExecutionsTotal(10);
        final DaoFeatureMetadata daoFeatureMetadata2 = createDaoFeatureMetadata(MODULE, FILE_NAME + "bbb", GLUE);
        daoFeatureMetadata2.setScenarioDefinitionsTotalCount(2);
        daoFeatureMetadata2.setScenarioExecutionsPassed(2);
        daoFeatureMetadata2.setScenarioExecutionsTotal(10);

        if (includeDuplicate) {
            testSuiteWithFeaturesMetadataTo.setFeatureMetadataList(Arrays.asList(daoFeatureMetadata1, daoFeatureMetadata1, daoFeatureMetadata2));
        } else {
            testSuiteWithFeaturesMetadataTo.setFeatureMetadataList(Arrays.asList(daoFeatureMetadata1, daoFeatureMetadata2));
        }

        buildRunWithTestSuitesTo.getTestSuiteWithFeaturesMetadataToes().add(testSuiteWithFeaturesMetadataTo);

        final TestSuiteWithFeaturesMetadataTo testSuiteWithFeaturesMetadataTo2 = createTestSuiteWithFeaturesMetadataTo();

        final DaoFeatureMetadata daoFeatureMetadata21 = createDaoFeatureMetadata(MODULE + "a", FILE_NAME + "aaa", GLUE);
        daoFeatureMetadata21.setScenarioDefinitionsTotalCount(2);
        daoFeatureMetadata21.setScenarioExecutionsPassed(2);
        daoFeatureMetadata21.setScenarioExecutionsTotal(10);
        final DaoFeatureMetadata daoFeatureMetadata22 = createDaoFeatureMetadata(MODULE + "a", FILE_NAME + "bbb", GLUE);
        daoFeatureMetadata22.setScenarioDefinitionsTotalCount(2);
        daoFeatureMetadata22.setScenarioExecutionsPassed(2);
        daoFeatureMetadata22.setScenarioExecutionsTotal(10);

        testSuiteWithFeaturesMetadataTo2.setFeatureMetadataList(Arrays.asList(daoFeatureMetadata21, daoFeatureMetadata22));

        buildRunWithTestSuitesTo.getTestSuiteWithFeaturesMetadataToes().add(testSuiteWithFeaturesMetadataTo2);

        // set up the mocks
        return Arrays.asList(buildRunWithTestSuitesTo);
    }

    /**
     * @return scenario definition with one step definition
     */
    public static ScenarioDefinition createScenarioWithOneStep() {
        final ScenarioDefinition scenarioDefinition = createScenarioDefinition();

        scenarioDefinition.setDescription(SCENARIO_DESCRIPTION);
        scenarioDefinition.setType(SCENARIO_TYPE);
        scenarioDefinition.setName(SCENARIO_NAME);
        scenarioDefinition.setExamples(Collections.singletonList(createRowTableDefinition()));
        scenarioDefinition.setScenarioExecutions(Collections.singletonList(createScenarioExecution()));
        scenarioDefinition.setStepDefinitions(Collections.singletonList(createStepDefinition()));
        scenarioDefinition.setScenarioExecutions(Collections.singletonList(createScenarioExecution()));

        return scenarioDefinition;
    }

    /**
     * create sample scenario execution
     */
    public static ScenarioExecution createScenarioExecution() {
        ScenarioExecution execution = new ScenarioExecution();

        execution.setBackgroundStepRuns(Collections.singletonList(
                createStepRun(StepStatus.PASSED)
        ));
        execution.setScenarioStepRuns(Arrays.asList(
                createStepRun(StepStatus.PASSED),
                createStepRun(StepStatus.PASSED)
        ));
        execution.setBeforeHookRuns(Collections.singletonList(
                createStepRun(StepStatus.MISSING)
        ));
        execution.setAfterHookRuns(Arrays.asList(
                createStepRun(StepStatus.MISSING),
                createStepRun(StepStatus.MISSING)
        ));

        return execution;
    }

    /**
     * Create step definition with step run and all possible step statuses
     */
    public static StepDefinition createStepDefinition() {
        final StepDefinition step = new StepDefinition();

        step.setName(STEP_KEYWORD + " " + STEP_NAME);
        step.setArguments(Collections.singletonList(createArgument()));
        step.setStepDataTable(createRowTableDefinition());

        return step;
    }

    /**
     * create sample argument
     */
    public static Argument createArgument() {
        Argument argument = new Argument();

        argument.setArgumentValue(ARGUMENT_PLACEHOLDER);
        argument.setOffset(ARG_OFFSET);

        return argument;
    }

    /**
     * Create step run with step status from arg.
     *
     * @param stepStatus to to use in step run
     * @return new step run sample
     */
    public static StepRun createStepRun(StepStatus stepStatus) {
        StepRun stepRun = new StepRun();
        stepRun.setStepStatus(stepStatus);
        stepRun.setDuration(STEP_DURATION);
        stepRun.setErrorMessage(STEP_ERR_MSG);
        stepRun.setEmbeddedIds(Collections.singletonList(STEP_EMBEDDING_ID));

        return stepRun;
    }

    /**
     * Create row table definition with preset data
     */
    public static RowTableDefinition createRowTableDefinition() {
        RowTableDefinition definition = new RowTableDefinition();
        definition.setDescription(ROW_TABLE_DESCRIPTION);

        definition.setTableHeader(createRowData(ROW_TABLE_HEADER_DESCRIPTION_COL, ROW_TABLE_HEADERS));
        definition.setRowData(Collections.singletonList(createRowData(ROW_TABLE_DESCRIPTION_VAL, ROW_TABLE_DATA_VAL)));
        return definition;
    }

    /**
     * Create new row data instance from given args.
     *
     * @param description value for description column
     * @param data        row data
     */
    public static RowData createRowData(String description, List<String> data) {
        RowData rowData = new RowData();
        rowData.setData(data);
        rowData.setDescription(description);

        return rowData;
    }

    /**
     * Create new scenario definition with set description, keyword, name
     */
    public static ScenarioDefinition createScenarioDefinition() {
        final ScenarioDefinition scenarioDefinition = new ScenarioDefinition();

        scenarioDefinition.setDescription(SCENARIO_DESCRIPTION);
        scenarioDefinition.setType(SCENARIO_TYPE);
        scenarioDefinition.setName(SCENARIO_NAME);

        scenarioDefinition.setExamples(Collections.singletonList(SampleData.createRowTableDefinition()));
        scenarioDefinition.setStepDefinitions(Collections.singletonList(SampleData.createStepDefinition()));
        scenarioDefinition.setScenarioExecutions(Collections.singletonList(createScenarioExecution()));

        return scenarioDefinition;
    }

    /**
     * @return sample of hook definition
     */
    public static HookDefinition createHookDefinition() {
        HookDefinition hookDefinition = new HookDefinition();
        hookDefinition.setLocation(LOCATION);
        hookDefinition.setArguments(Collections.singletonList(ARGUMENT));

        return hookDefinition;
    }

    /**
     * @return sample of hook report
     */
    public static HookDefinitionReport createHookDefinitionReport() {
        HookDefinitionReport hookReport = new HookDefinitionReport();
        hookReport.setLocation(LOCATION);

        cz.airbank.cucumber.reports.view.reports.model.feature.Argument argument
                = new cz.airbank.cucumber.reports.view.reports.model.feature.Argument();
        argument.setArgumentValue(HOOK_ARGUMENT_VALUE);
        argument.setOffset(HOOK_ARGUMENT_OFFSET);

        hookReport.setArguments(Collections.singletonList(argument));

        return hookReport;
    }

    /**
     * @return feature run
     */
    public static FeatureRun createFeatureRun(String module, String fileName, String glue) {
        final FeatureRun featureRun = new FeatureRun();

        featureRun.setDescription(FEATURE_RUN_DESC);
        featureRun.setName(FEATURE_RUN_NAME);
        featureRun.setMetadata(createDaoFeatureMetadata(module, fileName, glue));
        featureRun.setScenarioDefinitions(Arrays.asList(createScenarioWithOneStep(), createScenarioWithOneStep()));

        return featureRun;
    }

    /**
     * Create sample dao feature metadata.
     *
     * @param module   to set
     * @param fileName to set
     * @param glue     to set
     */
    public static DaoFeatureMetadata createDaoFeatureMetadata(String module, String fileName, String glue) {
        DaoFeatureMetadata featureMetadata = new DaoFeatureMetadata();

        featureMetadata.setModule(module);
        featureMetadata.setFilename(fileName);
        featureMetadata.setGlue(glue);
        featureMetadata.addScenarioExecutionsTotal(FEATURE_METADATA_SCENARIO_EXECUTIONS_TOTAL);
        featureMetadata.setScenarioExecutionsPassed(FEATURE_METADATA_SCENARIO_EXECUTIONS_PASSED);
        featureMetadata.addScenarioStepExecutionsTotal(FEATURE_METADATA_STEPS_TOTAL);
        featureMetadata.setScenarioStepExecutionsPassed(FEATURE_METADATA_STEPS_PASSED);

        return featureMetadata;
    }

    /**
     * @return create sample dao test suite metadata
     */
    public static DaoTestSuiteMetadata createDaoTestSuiteMetadata() {
        DaoTestSuiteMetadata metadata = new DaoTestSuiteMetadata();

        metadata.setSequentialNumber(DAO_TESTSUITE_METADATA_SEQ_NUMBER);
        metadata.setTestSuite(DAO_TESTSUITE_METADATA_SUITE);
        metadata.setPassed(PASSED);

        return metadata;
    }

    /**
     * @return create sample test suite presentation to
     */
    public static TestSuiteMetadataWithIdTo createTestSuitePresentationTo() {
        TestSuiteMetadataWithIdTo testSuiteMetadataWithIdTo = new TestSuiteMetadataWithIdTo();

        testSuiteMetadataWithIdTo.setId("id");
        testSuiteMetadataWithIdTo.setMetadata(createDaoTestSuiteMetadata());

        return testSuiteMetadataWithIdTo;
    }

    /**
     * @return create sample test suite presentation to
     */
    public static TestSuiteMetadataWithId createTestSuiteMetadataWithId() {
        TestSuiteMetadataWithId testSuiteMetadataWithId = new TestSuiteMetadataWithId();

        TestSuiteMetadata testSuiteMetadata = new TestSuiteMetadata();

        testSuiteMetadataWithId.setId("id");
        testSuiteMetadataWithId.setMetadata(testSuiteMetadata);

        return testSuiteMetadataWithId;
    }

    /**
     * @return create sample feature report to
     */
    public static FeatureReportTo createFeatureReportTo() {
        FeatureReportTo featureReportTo = new FeatureReportTo();

        featureReportTo.setFeatureRun(createFeatureRun(SampleData.MODULE, SampleData.FILE_NAME, SampleData.GLUE));
        featureReportTo.setTestSuiteMetadataWithIdTo(createTestSuitePresentationTo());
        featureReportTo.setBuildRunMetadataWithIdTo(createBuildRunMetadataWithTo());

        return featureReportTo;
    }

    /**
     * @return create sample build run metadata with id
     */
    public static BuildRunMetadataWithIdTo createBuildRunMetadataWithTo() {
        return new BuildRunMetadataWithIdTo();
    }

    /**
     * Create step result with given status
     */
    public static ResultReport<StepDefinitionReport> createStepResult(StepStatus status) {
        ResultReport<StepDefinitionReport> stepResult = new ResultReport<>();

        stepResult.setStatus(status);
        stepResult.setErrorMessage(STEP_ERR_MSG);
        stepResult.setDuration(STEP_DURATION);
        stepResult.setDefinitionReport(createStepReportDefinition());
        stepResult.setArguments(Collections.singletonList(createExecutionArgument()));

        return stepResult;
    }

    /**
     * Create argument for step definition
     */
    public static cz.airbank.cucumber.reports.view.reports.model.feature.Argument createDefinitionArgument() {
        cz.airbank.cucumber.reports.view.reports.model.feature.Argument arg =
                new cz.airbank.cucumber.reports.view.reports.model.feature.Argument();

        arg.setOffset(ARG_OFFSET);
        arg.setArgumentValue(ARGUMENT_PLACEHOLDER);

        return arg;
    }

    /**
     * Create argument for step result
     */
    public static cz.airbank.cucumber.reports.view.reports.model.feature.Argument createExecutionArgument() {
        cz.airbank.cucumber.reports.view.reports.model.feature.Argument arg =
                new cz.airbank.cucumber.reports.view.reports.model.feature.Argument();

        arg.setOffset(0);
        arg.setArgumentValue(ARGUMENT_REAL_VALUE);

        return arg;
    }

    /**
     * create sample step report definition
     */
    public static StepDefinitionReport createStepReportDefinition() {
        StepDefinitionReport stepDefinition = new StepDefinitionReport();

        stepDefinition.setArguments(Collections.singletonList(createDefinitionArgument()));
        stepDefinition.setStepDefinition(STEP_WITH_PLACEHOLDER);

        return stepDefinition;
    }

    /**
     * Create build run metadata instance.
     *
     * @param name       of build run
     * @param sequential number of build run
     */
    private static DaoBuildRunMetadata createBuildRunMetadata(String name, LocalDateTime buildAt, long sequential) {
        DaoBuildRunMetadata metadata = new DaoBuildRunMetadata();
        metadata.setSequentialNumber(sequential);
        metadata.setProjectName(name);
        metadata.setBuildAt(buildAt);

        return metadata;
    }

    /**
     * sample dao feature metadata
     */
    public static FeatureMetadata createFeatureMetadata() {
        FeatureMetadata featureMetadata = new FeatureMetadata();

        featureMetadata.setFilename(SampleData.FILE_NAME);
        featureMetadata.setGlue(SampleData.GLUE);
        featureMetadata.setModule(SampleData.MODULE);
        featureMetadata.setScenarioExecutionsPassed(FEATURE_METADATA_SCENARIO_EXECUTIONS_PASSED);
        featureMetadata.setScenarioExecutionsTotal(FEATURE_METADATA_SCENARIO_EXECUTIONS_TOTAL);
        featureMetadata.setScenarioStepExecutionsTotal(FEATURE_METADATA_STEPS_TOTAL);
        featureMetadata.setScenarioStepExecutionsPassed(FEATURE_METADATA_STEPS_PASSED);

        return featureMetadata;
    }

    /**
     * Create feature metadata with id from constants.
     */
    public static FeatureMetadataWithId createFeatureMetadataWithId() {
        FeatureMetadataWithId withId = new FeatureMetadataWithId();

        withId.setId(ID);
        withId.setMetadata(createFeatureMetadata());

        return withId;
    }

    /**
     * sample test suite with features
     */
    public static TestSuiteWithFeaturesMetadataTo createTestSuiteWithFeaturesMetadataTo() {
        TestSuiteWithFeaturesMetadataTo testSuiteWithFeaturesMetadataTo = new TestSuiteWithFeaturesMetadataTo();

        DaoFeatureMetadata metadata = createDaoFeatureMetadata(MODULE, FILE_NAME, GLUE);
        testSuiteWithFeaturesMetadataTo.setFeatureMetadataList(Collections.singletonList(metadata));
        testSuiteWithFeaturesMetadataTo.setTestSuiteMetadataWithIdTo(createTestSuitePresentationTo());

        return testSuiteWithFeaturesMetadataTo;
    }

    /**
     * sample build run with test suites to
     */
    public static BuildRunWithTestSuitesTo createBuildRunWithTestSuitesTo() {
        BuildRunWithTestSuitesTo buildRunWithTestSuitesTo = new BuildRunWithTestSuitesTo();

        TestSuiteWithFeaturesMetadataTo featuresMetadataTo = createTestSuiteWithFeaturesMetadataTo();
        buildRunWithTestSuitesTo.setTestSuiteWithFeaturesMetadataToes(Collections.singletonList(featuresMetadataTo));
        buildRunWithTestSuitesTo.setBuildRunMetadataWithIdTo(createBuildRunMetadataWithTo());

        return buildRunWithTestSuitesTo;
    }

    /**
     * sample test suite with features metadata
     */
    public static TestSuiteWithFeaturesMetadata createTestSuiteWithFeaturesMetadata() {
        TestSuiteWithFeaturesMetadata testSuiteWithFeaturesMetadata = new TestSuiteWithFeaturesMetadata();

        testSuiteWithFeaturesMetadata.setFeatureMetadataList(Collections.singletonList(createFeatureMetadata()));
        testSuiteWithFeaturesMetadata.setTestSuiteMetadataWithId(createTestSuiteMetadataWithId());

        return testSuiteWithFeaturesMetadata;
    }

    /**
     * sample project with runs
     */
    public static ProjectWithRunsTo createProjectWithRunsTo() {
        ProjectWithRunsTo projectWithRunsTo = new ProjectWithRunsTo();

        BuildRunWithTestSuitesTo runWithTestSuitesTo = createBuildRunWithTestSuitesTo();
        projectWithRunsTo.setBuildRunWithTestSuitesTos(Collections.singletonList(runWithTestSuitesTo));
        projectWithRunsTo.setProjectName(PROJECT_NAME);

        return projectWithRunsTo;
    }

    /**
     * Create scenario definition report from given arguments
     *
     * @param type            of scenario definition
     * @param stepDefinitions report will contain given number of step definitions
     */
    public static ScenarioDefinitionReport createScenarioDefinitionReport(ScenarioType type, int stepDefinitions) {
        ScenarioDefinitionReport scenarioDefinition = new ScenarioDefinitionReport();
        scenarioDefinition.setType(type);

        for (int i = 0; i < stepDefinitions; i++) {
            scenarioDefinition.getStepDefinitionReports().add(new StepDefinitionReport());
        }

        ScenarioRunReport run = new ScenarioRunReport();
        scenarioDefinition.getScenarioRunReports().add(run);

        for (int j = 0; j < stepDefinitions; j++) {
            run.getScenarioStepResults().add(createStepResult(StepStatus.PASSED));
        }

        return scenarioDefinition;
    }
}
