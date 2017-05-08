package cz.airbank.cucumber.reports.view.reports.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.dao.to.BuildRunWithTestSuitesTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteWithFeaturesMetadataTo;
import cz.airbank.cucumber.reports.view.SampleData;
import cz.airbank.cucumber.reports.view.model.BuildRunWithCounts;
import cz.airbank.cucumber.reports.view.model.TestSuiteWithFeaturesMetadata;
import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadataWithId;

/**
 * Tests for {@link BuildRunWithTestSuitesTo2BuildRunWithCountsMapper}.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class BuildRunWithTestSuitesTo2BuildRunWithCountsMapperTest {

    @Mock
    private BuildRunMetadataWithIdTo2BuildRunMetadataWithIdConverter buildRunMetadataWithIdConverter;

    @Mock
    private TestSuiteWithFeaturesMetadataTo2TestSuiteWithFeaturesMetadataConverter testSuiteWithFeaturesMetadataConverter;

    @InjectMocks
    private BuildRunWithTestSuitesTo2BuildRunWithCountsMapperImpl mapper;

    /**
     * Test conversion for non null input.
     */
    @Test
    public void testConvert_notNullInput() {
        BuildRunWithTestSuitesTo buildRunWithTestSuitesTo = SampleData.createBuildRunWithTestSuitesTo();
        List<TestSuiteWithFeaturesMetadataTo> testSuitesWithMetadata =
                buildRunWithTestSuitesTo.getTestSuiteWithFeaturesMetadataToes();
        BuildRunMetadataWithId exceptedMetadataWithId = new BuildRunMetadataWithId();
        TestSuiteWithFeaturesMetadata exceptedSuiteWithFeaturesMetadata = SampleData.createTestSuiteWithFeaturesMetadata();

        when(buildRunMetadataWithIdConverter
                .convert(buildRunWithTestSuitesTo.getBuildRunMetadataWithIdTo()))
                .thenReturn(exceptedMetadataWithId);
        when(testSuiteWithFeaturesMetadataConverter
                .convert(testSuitesWithMetadata.get(0)))
                .thenReturn(exceptedSuiteWithFeaturesMetadata);

        BuildRunWithCounts converted = mapper.convert(buildRunWithTestSuitesTo);

        assertSame(exceptedMetadataWithId, converted.getMetadataWithId());
        List<TestSuiteWithFeaturesMetadata> testSuiteWithCountsList = converted.getTestSuiteWithCountsList();
        assertEquals(1, testSuiteWithCountsList.size());
        assertSame(exceptedSuiteWithFeaturesMetadata, testSuiteWithCountsList.get(0));

        assertEquals(SampleData.FEATURE_METADATA_SCENARIO_EXECUTIONS_TOTAL, converted.getScenarioExecutionsTotal());
        assertEquals(SampleData.FEATURE_METADATA_SCENARIO_EXECUTIONS_PASSED, converted.getScenarioExecutionsPassed());
    }
}