package cz.airbank.cucumber.reports.api.converter;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.airbank.cucumber.reports.api.SampleData;
import cz.airbank.cucumber.reports.dao.entity.DaoBuildRunMetadata;

/**
 * Tests for {@link BuildRunMetadata2DaoBuildRunMetadataConverter} impl.
 *
 * @author Vaclav Stengl
 */
public class BuildRunMetadata2DaoBuildRunMetadataConverterTest {

    private BuildRunMetadata2DaoBuildRunMetadataConverter converter = new BuildRunMetadata2DaoBuildRunMetadataConverterImpl();

    @Test
    public void testConvert_notNullInput() {
        DaoBuildRunMetadata convertedMetadata = converter.convert(SampleData.createBuildRunMetadata());

        assertNotNull(convertedMetadata.getBuildAt());
        assertEquals(SampleData.ENVIRONMENT_NAME, convertedMetadata.getEnvironmentName());
        assertTrue(SampleData.TAGS.containsAll(convertedMetadata.getTags()));
        assertEquals(SampleData.EXECUTED_BY, convertedMetadata.getExecutedBy());
        assertEquals(SampleData.PROJECT_NAME, convertedMetadata.getProjectName());
        assertEquals(SampleData.SEQUENTIAL_NUMBER, convertedMetadata.getSequentialNumber());
    }
}