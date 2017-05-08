package cz.airbank.cucumber.reports.api.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.api.SampleData;
import cz.airbank.cucumber.reports.dao.entity.DaoFeatureMetadata;
import cz.airbank.cucumber.reports.dao.entity.FeatureRun;
import cz.airbank.cucumber.reports.transport.model.Feature;
import cz.airbank.cucumber.reports.transport.model.ScenarioDefinition;
import cz.airbank.cucumber.reports.transport.model.Statement;

/**
 * Tests for {@link Feature2FeatureRunConverter} impl.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class Feature2FeatureRunConverterTest extends CommonAttributesTestHelper {

    @Mock
    private ScenarioDefinition2ScenarioDefinitionConverter scenarioDefinitionConverter;

    @InjectMocks
    private Feature2FeatureRunConverterImpl converter;

    private Feature feature = SampleData.createFeature();

    @Override
    protected Statement returnStatementToConvert() {
        return feature;
    }

    /**
     * Feature conversion success.
     */
    @Test
    public void testConvert_notNullInput() {
        //source of step results for background and scenario step definitions

        final ScenarioDefinition background = feature.getBackground();
        final ScenarioDefinition scenario = feature.getScenarioDefinitionList().get(0);

        cz.airbank.cucumber.reports.dao.entity.ScenarioDefinition expectedBackground
            = new cz.airbank.cucumber.reports.dao.entity.ScenarioDefinition();
        cz.airbank.cucumber.reports.dao.entity.ScenarioDefinition expectedScenario
            = new cz.airbank.cucumber.reports.dao.entity.ScenarioDefinition();

        when(scenarioDefinitionConverter.convert(background)).thenReturn(expectedBackground);
        when(scenarioDefinitionConverter.convert(scenario)).thenReturn(expectedScenario);
        //conversion
        FeatureRun featureRun = converter.convert(feature);

        assertTaggedStatement(featureRun);
        DaoFeatureMetadata metadata = featureRun.getMetadata();
        assertEquals(SampleData.FILE_NAME, metadata.getFilename());
        assertEquals(SampleData.MODULE, metadata.getModule());
        assertEquals(SampleData.GLUE, metadata.getGlue());

        assertNotNull(featureRun.getScenarioDefinitions());
        assertEquals(1, featureRun.getScenarioDefinitions().size());
        assertSame(expectedScenario, featureRun.getScenarioDefinitions().get(0));

        assertSame(expectedBackground, featureRun.getBackground());
    }
}