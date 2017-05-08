package cz.airbank.cucumber.reports.dao.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.dao.entity.DaoFeatureMetadata;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.to.TestSuiteMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteWithFeaturesMetadataTo;
import cz.airbank.cucumber.reports.dao.util.SampleData;

/**
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class TestSuiteExecution2TestSuiteWithFeaturesMetadataToConverterTest {

    @Mock
    private TestSuiteExecution2TestSuiteMetadataWithIdToConverter testSuiteMetadataWithIdToConverter;

    @InjectMocks
    private TestSuiteExecution2TestSuiteWithFeaturesMetadataToConverterImpl converter;

    @Test
    public void testConvert() {
        TestSuiteExecution execution = SampleData.createTestSuiteExecution();
        TestSuiteMetadataWithIdTo expectedMetadataWithId = SampleData.createTestSuiteMetadataWithIdTo();
        DaoFeatureMetadata exceptedMetadata = execution.getFeatureRuns().get(0).getMetadata();

        when(testSuiteMetadataWithIdToConverter.convert(execution)).thenReturn(expectedMetadataWithId);

        TestSuiteWithFeaturesMetadataTo converted = converter.convert(execution);

        assertSame(expectedMetadataWithId, converted.getTestSuiteMetadataWithIdTo());
        assertEquals(1, converted.getFeatureMetadataList().size());
        assertSame(exceptedMetadata, converted.getFeatureMetadataList().get(0));
    }

}