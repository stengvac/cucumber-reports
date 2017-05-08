package cz.airbank.cucumber.reports.view.reports.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.dao.entity.DaoFeatureMetadata;
import cz.airbank.cucumber.reports.dao.to.TestSuiteWithFeaturesMetadataTo;
import cz.airbank.cucumber.reports.view.SampleData;
import cz.airbank.cucumber.reports.view.model.TestSuiteWithFeaturesMetadata;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadata;
import cz.airbank.cucumber.reports.view.reports.model.testsuite.TestSuiteMetadataWithId;

/**
 * Tests for {@link TestSuiteWithFeaturesMetadataTo2TestSuiteWithFeaturesMetadataConverter} impl.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class TestSuiteWithFeaturesMetadataTo2TestSuiteWithFeaturesMetadataConverterTest {

    @Mock
    private TestSuiteMetadataWithIdTo2TestSuiteMetadataWithIdConverter testSuiteMetadataWithIdConverter;

    @Mock
    private DaoFeatureMetadata2FeatureMetadataConverter featureMetadataConverter;

    @InjectMocks
    private TestSuiteWithFeaturesMetadataTo2TestSuiteWithFeaturesMetadataConverter testedConverter
            = new TestSuiteWithFeaturesMetadataTo2TestSuiteWithFeaturesMetadataConverterImpl();

    @Test
    public void testConvert() {
        TestSuiteWithFeaturesMetadataTo suiteWithFeaturesMetadataTo = SampleData.createTestSuiteWithFeaturesMetadataTo();
        List<DaoFeatureMetadata> metadataList = suiteWithFeaturesMetadataTo.getFeatureMetadataList();
        FeatureMetadata expectedMetadata = SampleData.createFeatureMetadata();
        TestSuiteMetadataWithId expectedMetadataWithId = SampleData.createTestSuiteMetadataWithId();

        when(featureMetadataConverter.convert(metadataList.get(0))).thenReturn(expectedMetadata);
        when(testSuiteMetadataWithIdConverter
                .convert(suiteWithFeaturesMetadataTo.getTestSuiteMetadataWithIdTo()))
                .thenReturn(expectedMetadataWithId);

        TestSuiteWithFeaturesMetadata converted = testedConverter.convert(suiteWithFeaturesMetadataTo);

        List<FeatureMetadata> featureMetadataList = converted.getFeatureMetadataList();
        assertEquals(1, featureMetadataList.size());
        assertSame(expectedMetadata, featureMetadataList.get(0));
    }
}