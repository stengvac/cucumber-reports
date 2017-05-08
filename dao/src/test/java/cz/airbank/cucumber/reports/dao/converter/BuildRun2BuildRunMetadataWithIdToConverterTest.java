package cz.airbank.cucumber.reports.dao.converter;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.to.BuildRunMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.util.SampleData;

/**
 * Tests for {@link BuildRun2BuildRunMetadataWithIdToConverter}
 *
 * @author Vaclav Stengl
 */
public class BuildRun2BuildRunMetadataWithIdToConverterTest {

    private BuildRun2BuildRunMetadataWithIdToConverter converter = new BuildRun2BuildRunMetadataWithIdToConverterImpl();

    /**
     * Test successful conversion.
     */
    @Test
    public void testConvert_success() {
        BuildRun buildRun = SampleData.createBuildRun();

        BuildRunMetadataWithIdTo converted = converter.convert(buildRun);

        assertSame(buildRun.getMetadata(), converted.getMetadata());
        assertEquals(SampleData.BUILD_RUN_ID, converted.getId());
    }
}