package cz.airbank.cucumber.reports.api.converter;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.airbank.cucumber.reports.api.SampleData;
import cz.airbank.cucumber.reports.dao.entity.DaoTestSuiteMetadata;

/**
 * Tests for {@link TestSuiteMetadata2DaoTestSuiteMetadataConverter} impl.
 *
 * @author Vaclav Stengl
 */
public class TestSuiteMetadata2DaoTestSuiteMetadataConverterTest {

    private TestSuiteMetadata2DaoTestSuiteMetadataConverter converter = new TestSuiteMetadata2DaoTestSuiteMetadataConverterImpl();

    @Test
    public void testConvert_notNullInput() {
        DaoTestSuiteMetadata converted = converter.convert(SampleData.createTestSuiteMetadata());

        assertEquals(1, converted.getEnvVariables().size());
        for (String key : converted.getEnvVariables().keySet()) {
            assertTrue(SampleData.ENV_VARS.containsKey(key));
        }
        assertEquals(SampleData.SEQUENTIAL_NUMBER, converted.getSequentialNumber());
        assertEquals(SampleData.TEST_SUITE, converted.getTestSuite());
    }
}