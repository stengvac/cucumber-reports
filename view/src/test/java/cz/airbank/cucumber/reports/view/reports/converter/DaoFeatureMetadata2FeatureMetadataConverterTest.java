package cz.airbank.cucumber.reports.view.reports.converter;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.airbank.cucumber.reports.view.SampleData;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadata;

/**
 * Tests for {@link DaoFeatureMetadata2FeatureMetadataConverter} impl.
 *
 * @author Vaclav Stengl
 */
public class DaoFeatureMetadata2FeatureMetadataConverterTest {

    private DaoFeatureMetadata2FeatureMetadataConverter converter = new DaoFeatureMetadata2FeatureMetadataConverterImpl();

    @Test
    public void testConvert() {
        FeatureMetadata converted = converter
                .convert(SampleData.createDaoFeatureMetadata(SampleData.MODULE, SampleData.FILE_NAME, SampleData.GLUE));

        assertEquals(SampleData.GLUE, converted.getGlue());
        assertEquals(SampleData.MODULE, converted.getModule());
        assertEquals(SampleData.FILE_NAME, converted.getFilename());
        assertEquals(SampleData.FEATURE_METADATA_SCENARIO_EXECUTIONS_TOTAL, converted.getScenarioExecutionsTotal());
        assertEquals(SampleData.FEATURE_METADATA_SCENARIO_EXECUTIONS_PASSED, converted.getScenarioExecutionsPassed());
        assertEquals(SampleData.FEATURE_METADATA_STEPS_TOTAL, converted.getScenarioStepExecutionsTotal());
        assertEquals(SampleData.FEATURE_METADATA_STEPS_PASSED, converted.getScenarioStepExecutionsPassed());
    }

}