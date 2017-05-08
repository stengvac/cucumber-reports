package cz.airbank.cucumber.reports.dao.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.dao.entity.FeatureRun;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.to.BuildRunMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.FeatureReportTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.util.SampleData;

/**
 * Test for {@link FeatureReportToConverter}
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class FeatureReportToConverterTest {

    @Mock
    private TestSuiteExecution2TestSuiteMetadataWithIdToConverter testSuitePresentationToConverter;

    @InjectMocks
    private FeatureReportToConverterImpl converter;

    /**
     * Successful conversion.
     */
    @Test
    public void testConvert_success() {
        TestSuiteExecution execution = SampleData.createTestSuiteExecution();
        //real query will contain only one feature run or whole execution will be null when not found
        FeatureRun featureRun = execution.getFeatureRuns().get(0);
        TestSuiteMetadataWithIdTo testSuiteMetadataWithIdTo = new TestSuiteMetadataWithIdTo();
        BuildRunMetadataWithIdTo buildRunMetadataWithIdTo = new BuildRunMetadataWithIdTo();

        when(testSuitePresentationToConverter.convert(execution)).thenReturn(testSuiteMetadataWithIdTo);

        FeatureReportTo converted = converter.convert(execution, buildRunMetadataWithIdTo);

        assertSame(testSuiteMetadataWithIdTo, converted.getTestSuiteMetadataWithIdTo());
        assertSame(featureRun, converted.getFeatureRun());
        assertSame(buildRunMetadataWithIdTo, converted.getBuildRunMetadataWithIdTo());
    }
}