package cz.airbank.cucumber.reports.api.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.api.SampleData;
import cz.airbank.cucumber.reports.dao.entity.Argument;
import cz.airbank.cucumber.reports.dao.entity.StepRun;
import cz.airbank.cucumber.reports.transport.model.StepResult;
import cz.airbank.cucumber.reports.transport.model.StepStatus;

/**
 * Tests for {@link StepResult2StepRunConverter} impl.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class StepResult2StepRunConverterTest {

    @Mock
    private Argument2ArgumentConverter argumentConverter;

    @InjectMocks
    private StepResult2StepRunConverter converter = new StepResult2StepRunConverterImpl();

    @Test
    public void testConvert_notNullInput() {
        StepResult stepResult = SampleData.createStepResult(StepStatus.PASSED);

        cz.airbank.cucumber.reports.transport.model.Argument executionArgument = stepResult.getArguments().get(0);
        Argument expectedArgument = new Argument();
        when(argumentConverter.convert(executionArgument)).thenReturn(expectedArgument);

        StepRun converted = converter.convert(stepResult);

        assertEquals(SampleData.STEP_DURATION, converted.getDuration());
        assertEquals(SampleData.STEP_ERR_MSG, converted.getErrorMessage());
        assertEquals(StepStatus.PASSED.name(), converted.getStepStatus().name());
        assertEquals(1, converted.getEmbeddedIds().size());
        assertEquals(SampleData.EMBEDDING_ID, converted.getEmbeddedIds().get(0));
        assertEquals(1, converted.getArguments().size());
        assertEquals(expectedArgument, converted.getArguments().get(0));
    }
}