package cz.airbank.cucumber.reports.api.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.api.SampleData;
import cz.airbank.cucumber.reports.dao.entity.RowTableDefinition;
import cz.airbank.cucumber.reports.dao.entity.ScenarioExecution;
import cz.airbank.cucumber.reports.dao.entity.StepDefinition;
import cz.airbank.cucumber.reports.transport.model.ScenarioDefinition;
import cz.airbank.cucumber.reports.transport.model.ScenarioRun;
import cz.airbank.cucumber.reports.transport.model.ScenarioType;
import cz.airbank.cucumber.reports.transport.model.Statement;

/**
 * Tests for {@link ScenarioDefinition2ScenarioDefinitionConverter} impl.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class ScenarioDefinition2ScenarioDefinitionConverterTest extends CommonAttributesTestHelper {

    @Mock
    private DataTable2RowTableDefinitionConverter rowTableDefinitionConverter;

    @Mock
    private StepDefinition2StepDefinitionConverter stepDefinitionConverter;

    @Mock
    private ScenarioRun2ScenarioExecutionConverter scenarioExecutionConverter;

    @InjectMocks
    private ScenarioDefinition2ScenarioDefinitionConverter converter = new ScenarioDefinition2ScenarioDefinitionConverterImpl();

    private ScenarioDefinition scenarioDefinition = SampleData.createScenarioDefinition(ScenarioType.BACKGROUND,
            Collections.singletonList(new ScenarioRun()));

    @Override
    protected Statement returnStatementToConvert() {
        return scenarioDefinition;
    }

    /**
     * Test whenever converter work properly.
     */
    @Test
    public void testConvertFeatureRun_notNull() {
        ScenarioExecution expectedScenarioExecution = new ScenarioExecution();
        List<RowTableDefinition> expectedExamples = Collections.singletonList(new RowTableDefinition());
        StepDefinition expectedStepDefinition = new StepDefinition();

        when(rowTableDefinitionConverter.oneToList(scenarioDefinition.getExamples())).thenReturn(expectedExamples);
        when(scenarioExecutionConverter.convert(scenarioDefinition.getScenarioRunList().get(0))).thenReturn(expectedScenarioExecution);
        when(stepDefinitionConverter.convert(scenarioDefinition.getStepDefinitionList().get(0))).thenReturn(expectedStepDefinition);

        cz.airbank.cucumber.reports.dao.entity.ScenarioDefinition converted = converter.convert(scenarioDefinition);

        assertTaggedStatement(converted);
        assertEquals(cz.airbank.cucumber.reports.common.model.ScenarioType.BACKGROUND, converted.getType());
        assertSame(expectedExamples, converted.getExamples());
        assertEquals(1, converted.getScenarioExecutions().size());
        assertSame(expectedScenarioExecution, converted.getScenarioExecutions().get(0));
        assertEquals(1, scenarioDefinition.getStepDefinitionList().size());
        assertSame(expectedStepDefinition, converted.getStepDefinitions().get(0));
    }
}