package cz.airbank.cucumber.reports.api.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.api.SampleData;
import cz.airbank.cucumber.reports.dao.entity.ScenarioExecution;
import cz.airbank.cucumber.reports.dao.entity.StepRun;
import cz.airbank.cucumber.reports.transport.model.ScenarioRun;
import cz.airbank.cucumber.reports.transport.model.StepResult;

/**
 * Tests for {@link ScenarioRun2ScenarioExecutionConverter} impl.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class ScenarioRun2ScenarioExecutionConverterTest {

    @Mock
    private StepResult2StepRunConverter stepRunConverter;

    @InjectMocks
    private ScenarioRun2ScenarioExecutionConverterImpl converter;

    @Test
    public void testConvert_notNullInput() {
        ScenarioRun scenarioRun = SampleData.createScenarioRun();
        StepResult backgroundResult = scenarioRun.getBackgroundStepResults().get(0);
        StepResult scenarioResult = scenarioRun.getScenarioStepResults().get(0);

        StepRun backgroundStepRun = new StepRun();
        StepRun scenarioStepRun = new StepRun();

        when(stepRunConverter.convert(backgroundResult)).thenReturn(backgroundStepRun);
        when(stepRunConverter.convert(scenarioResult)).thenReturn(scenarioStepRun);

        ScenarioExecution converted = converter.convert(scenarioRun);

        assertEquals(1, converted.getBackgroundStepRuns().size());
        assertSame(backgroundStepRun, converted.getBackgroundStepRuns().get(0));
        assertEquals(1, converted.getScenarioStepRuns().size());
        assertSame(scenarioStepRun, converted.getScenarioStepRuns().get(0));
    }
}