package cz.airbank.cucumber.reports.dao.converter;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.airbank.cucumber.reports.dao.entity.DaoTestSuiteMetadata;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.to.TestSuiteMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.util.SampleData;

/**
 * Test for {@link TestSuiteExecution2TestSuiteMetadataWithIdToConverter}
 *
 * @author Vaclav Stengl
 */
public class TestSuiteExecution2TestSuiteMetadataWithIdToTest {

    private TestSuiteExecution2TestSuiteMetadataWithIdToConverter converter = new TestSuiteExecution2TestSuiteMetadataWithIdToConverterImpl();

    /**
     * Successful conversion.
     */
    @Test
    public void testConvert() {
        TestSuiteExecution execution = SampleData.createTestSuiteExecution();
        DaoTestSuiteMetadata metadata = execution.getMetadata();

        TestSuiteMetadataWithIdTo converted = converter.convert(execution);

        assertSame(metadata, converted.getMetadata());
        assertEquals(SampleData.TEST_SUITE_ID, converted.getId());
    }
}