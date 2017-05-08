package cz.airbank.cucumber.reports.view.reports.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.common.model.ScenarioType;
import cz.airbank.cucumber.reports.dao.entity.ScenarioDefinition;
import cz.airbank.cucumber.reports.dao.to.FeatureReportTo;
import cz.airbank.cucumber.reports.view.SampleData;
import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadataWithId;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadataWithId;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.ScenarioDefinitionReport;
import cz.airbank.cucumber.reports.view.reports.model.testsuite.TestSuiteMetadataWithId;

/**
 * Tests for {@link ScenarioRun2FeatureReportConverter} class.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class ScenarioRun2FeatureReportConverterTest {

    @Mock
    private BuildRunMetadataWithIdTo2BuildRunMetadataWithIdConverter buildRunMetadataWithIdToConverter;

    @Mock
    private TestSuiteMetadataWithIdTo2TestSuiteMetadataWithIdConverter testSuiteMetadataWithIdConverter;

    @Mock
    private FeatureRun2FeatureMetadataWithIdConverter featureMetadataConverter;

    @Mock
    private ScenarioDefinition2ScenarioDefinitionReportConverter scenarioDefinitionReportConverter;

    @Mock
    private LineStatement2LineStatementReportConverter lineStatementReportConverter;

    @InjectMocks
    private ScenarioRun2FeatureReportConverter converter = new ScenarioRun2FeatureReportConverterImpl();

    private FeatureReportTo reportTo;
    private FeatureMetadataWithId exceptedFeatureMetadataWithId = new FeatureMetadataWithId();
    private TestSuiteMetadataWithId exceptedTestSuiteMetadataWithId = new TestSuiteMetadataWithId();
    private BuildRunMetadataWithId exceptedBuildRunMetadataWithId = new BuildRunMetadataWithId();

    @Before
    public void setUp() {
        reportTo = SampleData.createFeatureReportTo();

        when(featureMetadataConverter.convert(reportTo.getFeatureRun())).thenReturn(exceptedFeatureMetadataWithId);
        when(testSuiteMetadataWithIdConverter.convert(reportTo.getTestSuiteMetadataWithIdTo()))
            .thenReturn(exceptedTestSuiteMetadataWithId);
        when(buildRunMetadataWithIdToConverter.convert(reportTo.getBuildRunMetadataWithIdTo()))
            .thenReturn(exceptedBuildRunMetadataWithId);
    }

    /**
     * Test whenever data fields of feature report are set correctly.
     */
    @Test
    public void testConvert_NameAndDescription() {
        //remove all unnecessary data
        reportTo.getFeatureRun().setScenarioDefinitions(null);

        FeatureReport report = converter.convert(reportTo);

        assertNotNull(report);
        assertEquals(SampleData.FEATURE_RUN_DESC, report.getDescription());
        assertEquals(SampleData.FEATURE_RUN_NAME, report.getName());
    }

    /**
     * All necessary metadata converted.
     */
    @Test
    public void testConvert_metadata() {
        reportTo.getFeatureRun().setScenarioDefinitions(null);

        FeatureReport report = converter.convert(reportTo);

        assertSame(exceptedBuildRunMetadataWithId, report.getBuildRunMetadataWithId());
        assertSame(exceptedFeatureMetadataWithId, report.getFeatureMetadataWithId());
        assertSame(exceptedTestSuiteMetadataWithId, report.getTestSuiteMetadataWithId());
    }

    /**
     * Test whenever conversion is performed correctly with background present (bg also have its step definitions).
     */
    @Test
    public void testConvert_withBackground() {
        ScenarioDefinition background = new ScenarioDefinition();
        ScenarioDefinition scenario = new ScenarioDefinition();

        reportTo.getFeatureRun().setBackground(background);
        reportTo.getFeatureRun().setScenarioDefinitions(Collections.singletonList(scenario));

        ScenarioDefinitionReport expectedBackground = SampleData.createScenarioDefinitionReport(ScenarioType.BACKGROUND, 2);
        ScenarioDefinitionReport expectedScenario = SampleData.createScenarioDefinitionReport(ScenarioType.SCENARIO, 1);

        when(scenarioDefinitionReportConverter.convert(background))
            .thenReturn(expectedBackground);
        when(scenarioDefinitionReportConverter.convert(scenario))
            .thenReturn(expectedScenario);

        FeatureReport report = converter.convert(reportTo);

        assertNotNull(report);
        assertSame(expectedBackground, report.getBackground());
        assertEquals(1, report.getScenarioDefinitionList().size());
        assertSame(expectedScenario, report.getScenarioDefinitionList().get(0));
    }

    /**
     * Conversion without background present. So there are no background steps in scenario runs.
     */
    @Test
    public void testConvert_withoutBackground() {
        ScenarioDefinition scenario = new ScenarioDefinition();

        reportTo.getFeatureRun().setScenarioDefinitions(Collections.singletonList(scenario));

        ScenarioDefinitionReport expectedScenario = SampleData.createScenarioDefinitionReport(ScenarioType.SCENARIO, 1);
        when(scenarioDefinitionReportConverter.convert(scenario)).thenReturn(expectedScenario);

        FeatureReport report = converter.convert(reportTo);

        assertNotNull(report);
        assertNull(report.getBackground());

        assertEquals(1, report.getScenarioDefinitionList().size());
        assertSame(expectedScenario, report.getScenarioDefinitionList().get(0));
    }
}