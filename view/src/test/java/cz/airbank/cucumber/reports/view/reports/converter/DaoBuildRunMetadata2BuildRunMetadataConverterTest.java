package cz.airbank.cucumber.reports.view.reports.converter;

import static junit.framework.TestCase.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import cz.airbank.cucumber.reports.dao.entity.DaoBuildRunMetadata;
import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadata;

/**
 * Tests for {@link DaoBuildRunMetadata2BuildRunMetadataConverter}
 *
 * @author Vaclav Stengl
 */
public class DaoBuildRunMetadata2BuildRunMetadataConverterTest {

    private static final String NAME = "name";
    private static final LocalDateTime BUILD_AT = LocalDateTime.of(2017, Month.APRIL, 15, 4, 5);
    private static final long SEQUENTIAL_NUMBER = 4;

    private DaoBuildRunMetadata2BuildRunMetadataConverter converter = new DaoBuildRunMetadata2BuildRunMetadataConverterImpl();

    /**
     * Conversion performed correctly
     */
    @Test
    public void testConvert() {
        DaoBuildRunMetadata metadata = new DaoBuildRunMetadata();
        metadata.setSequentialNumber(SEQUENTIAL_NUMBER);
        metadata.setBuildAt(BUILD_AT);
        metadata.setProjectName(NAME);

        BuildRunMetadata converted = converter.convert(metadata);

        assertEquals(BUILD_AT, converted.getBuildAt());
        assertEquals(NAME, converted.getProjectName());
        assertEquals(SEQUENTIAL_NUMBER, converted.getSequentialNumber());
    }
}