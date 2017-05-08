package cz.airbank.cucumber.reports.dao.converter;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.to.FeatureMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteReportTo;
import cz.airbank.cucumber.reports.dao.util.SampleData;

/**
 * Tests for {@link TestSuiteReportToConverter}
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class TestSuiteReportToConverterTest {

    @Mock
    private FeatureRun2FeatureMetadataWithIdToConverter featureRun2FeatureMetadataWithIdToConverter;

    @Mock
    private TestSuiteExecution2TestSuiteMetadataWithIdToConverter testSuiteMetadataWithIdToConverter;

    @InjectMocks
    private TestSuiteReportToConverter converter = new TestSuiteReportToConverterImpl();

    /**
     * Test successful conversion.
     */
    @Test
    public void testConvert_success() {
        TestSuiteExecution testSuiteExecution = SampleData.createTestSuiteExecution();
        FeatureMetadataWithIdTo expectedFeatureMetadataWithIdTo = new FeatureMetadataWithIdTo();
        TestSuiteMetadataWithIdTo testSuiteMetadataWithIdTo = new TestSuiteMetadataWithIdTo();

        when(featureRun2FeatureMetadataWithIdToConverter
            .convert(testSuiteExecution.getFeatureRuns().get(0)))
            .thenReturn(expectedFeatureMetadataWithIdTo);
        when(testSuiteMetadataWithIdToConverter.convert(testSuiteExecution))
            .thenReturn(testSuiteMetadataWithIdTo);

        TestSuiteReportTo converted = converter.convert(testSuiteExecution);

        assertSame(testSuiteMetadataWithIdTo, converted.getTestSuiteMetadataWithIdTo());
        assertEquals(1, converted.getFeatureMetadataWithIdTos().size());
        assertSame(expectedFeatureMetadataWithIdTo, converted.getFeatureMetadataWithIdTos().get(0));
    }
}