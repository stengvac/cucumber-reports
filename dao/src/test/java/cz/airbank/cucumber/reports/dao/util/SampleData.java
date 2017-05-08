package cz.airbank.cucumber.reports.dao.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.test.util.ReflectionTestUtils;

import cz.airbank.cucumber.reports.common.model.ScenarioType;
import cz.airbank.cucumber.reports.common.model.StepStatus;
import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.entity.DaoBuildRunMetadata;
import cz.airbank.cucumber.reports.dao.entity.DaoFeatureMetadata;
import cz.airbank.cucumber.reports.dao.entity.ScenarioExecution;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.entity.FeatureRun;
import cz.airbank.cucumber.reports.dao.entity.RowData;
import cz.airbank.cucumber.reports.dao.entity.RowTableDefinition;
import cz.airbank.cucumber.reports.dao.entity.ScenarioDefinition;
import cz.airbank.cucumber.reports.dao.entity.StepDefinition;
import cz.airbank.cucumber.reports.dao.entity.StepRun;
import cz.airbank.cucumber.reports.dao.entity.DaoTestSuiteMetadata;
import cz.airbank.cucumber.reports.dao.to.TestSuiteMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.BuildRunMetadataWithIdTo;

/**
 * Util class to build sample entities.
 *
 * @author David Passler
 */
public class SampleData {

    public static final String PROJECT_NAME = "projectName";
    public static final long SEQUENTIAL_NUMBER = 50;
    public static final String TEST_SUITE_ID = "id";
    public static final String TEST_SUITE = "tstSuite";
    public static final String ID_KEY = "_id";
    public static final String BUILD_RUN_ID = "bid";
    public static final String BUILD_PROJECT_NAME = "bproj";
    public static final long BUILD_SEQUENTIAL_NUMBER = 55;

    /**
     * create sample build run
     */
    public static DaoTestSuiteMetadata createDaoTestSuiteMetadata() {
        DaoTestSuiteMetadata metadata = new DaoTestSuiteMetadata();

        metadata.setTestSuite(TEST_SUITE);
        metadata.setSequentialNumber(SEQUENTIAL_NUMBER);

        return metadata;
    }

    /**
     * create sample test suite metadata TO with id
     */
    public static TestSuiteMetadataWithIdTo createTestSuiteMetadataWithIdTo() {

        TestSuiteMetadataWithIdTo testSuiteMetadataWithIdTo = new TestSuiteMetadataWithIdTo();
        testSuiteMetadataWithIdTo.setId(TEST_SUITE_ID);
        testSuiteMetadataWithIdTo.setMetadata(createDaoTestSuiteMetadata());

        return testSuiteMetadataWithIdTo;
    }


    /**
     * @return sample test suite execution
     */
    public static TestSuiteExecution createTestSuiteExecution() {
        final TestSuiteExecution testSuiteExecution = new TestSuiteExecution();
        ReflectionTestUtils.setField(testSuiteExecution, "id", TEST_SUITE_ID);

        testSuiteExecution.setMetadata(createDaoTestSuiteMetadata());
        testSuiteExecution.setFeatureRuns(Collections.singletonList(createSampleFeatureRun(1)));

        return testSuiteExecution;
    }

    /**
     * @return sample build run
     */
    public static BuildRun createBuildRun() {
        BuildRun buildRun = new BuildRun();

        ReflectionTestUtils.setField(buildRun, "id", BUILD_RUN_ID);
        buildRun.setMetadata(createDaoBuildRunMetadata());
        buildRun.setTestSuites(Collections.singletonList(createTestSuiteExecution()));

        return buildRun;
    }

    /**
     * @return sample build run metadata with id
     */
    public static BuildRunMetadataWithIdTo createBuildRunMetadataWithIdTo() {
        BuildRunMetadataWithIdTo buildRunMetadataWithIdTo = new BuildRunMetadataWithIdTo();

        buildRunMetadataWithIdTo.setId(BUILD_RUN_ID);
        buildRunMetadataWithIdTo.setMetadata(createDaoBuildRunMetadata());

        return buildRunMetadataWithIdTo;
    }

    /**
     * @return sample build run metadata
     */
    public static DaoBuildRunMetadata createDaoBuildRunMetadata() {
        DaoBuildRunMetadata metadata = new DaoBuildRunMetadata();

        metadata.setProjectName(BUILD_PROJECT_NAME);
        metadata.setSequentialNumber(BUILD_SEQUENTIAL_NUMBER);

        return metadata;
    }

    /**
     * @return sample feature run
     */
    private static FeatureRun createSampleFeatureRun(int i) {
        final FeatureRun featureRun = new FeatureRun();
        DaoFeatureMetadata metadata = new DaoFeatureMetadata();
        metadata.setModule("Module" + i);
        metadata.setFilename("FeatureFilename" + i);
        metadata.setGlue("glue" + i);

        featureRun.setMetadata(metadata);
        featureRun.setId(0);
        featureRun.setName("FeatureName" + i);
        featureRun.setDescription("Description" + i);
        featureRun
            .setScenarioDefinitions(Arrays.asList(createSampleScenarioDefinition(1),
                                                  createSampleScenarioDefinition(1),
                                                  createSampleScenarioDefinition(2)));

        return featureRun;
    }

    /**
     * @return sample scenario definition
     */
    private static ScenarioDefinition createSampleScenarioDefinition(int i) {
        final ScenarioDefinition scenarioDefinition = new ScenarioDefinition();

        scenarioDefinition.setDescription("ScenarioDefinition" + i);
        scenarioDefinition.setType(ScenarioType.values()[ThreadLocalRandom.current().nextInt(ScenarioType.values().length)]);
        scenarioDefinition.setName("ScenarioDefinitionName" + i);

        ScenarioExecution execution = new ScenarioExecution();
        execution.setBackgroundStepRuns(Collections.singletonList(createSampleStepRun(true)));
        execution.setScenarioStepRuns(Collections.singletonList(createSampleStepRun(false)));

        scenarioDefinition.setScenarioExecutions(Collections.singletonList(execution));
        scenarioDefinition.setExamples(Collections.singletonList(createSampleRowTableDefinition(1)));
        scenarioDefinition.setStepDefinitions(Collections.singletonList(createSampleStepDefinition(i)));

        return scenarioDefinition;
    }

    /**
     * @return sample step definition
     */
    private static StepDefinition createSampleStepDefinition(int i) {
        final StepDefinition stepDefinition = new StepDefinition();

        stepDefinition.setName("StepDefinition" + i);

        return stepDefinition;
    }

    /**
     * @return sample row table definition
     */
    private static RowTableDefinition createSampleRowTableDefinition(int i) {
        final RowTableDefinition rowTableDefinition = new RowTableDefinition();
        rowTableDefinition.setDescription("RowTableDefinition" + i);
        RowData header = new RowData();
        header.setData(Arrays.asList("column1", "column2", "column3"));
        rowTableDefinition.setTableHeader(header);

        rowTableDefinition.setRowData(Arrays.asList(createSampleRowData(1), createSampleRowData(2), createSampleRowData(3)));

        return rowTableDefinition;
    }

    /**
     * @return sample row data
     */
    private static RowData createSampleRowData(int i) {
        final RowData rowData = new RowData();
        rowData.setDescription("RowDataDescription" + i);
        rowData.setData(Arrays.asList("data1", "data2", "data3"));

        return rowData;
    }

    /**
     * @return sample step run
     */
    private static StepRun createSampleStepRun(boolean success) {
        final StepRun stepRun = new StepRun();

        stepRun.setDuration(ThreadLocalRandom.current().nextLong(100));

        if (!success) {
            stepRun.setEmbeddedIds(Collections.singletonList("StepRunErrorImage"));
            stepRun.setErrorMessage("StepRunErrorMessage");
            stepRun.setStepStatus(StepStatus.FAILED);
        } else {
            stepRun.setStepStatus(StepStatus.PASSED);
        }

        return stepRun;
    }
}
