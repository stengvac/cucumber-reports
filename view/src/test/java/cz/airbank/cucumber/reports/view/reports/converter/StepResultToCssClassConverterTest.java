package cz.airbank.cucumber.reports.view.reports.converter;

import static org.junit.Assert.*;

import java.util.stream.Stream;

import org.junit.Test;

import cz.airbank.cucumber.reports.common.model.StepStatus;
import cz.airbank.cucumber.reports.view.SampleData;

/**
 * Tests for {@link StepResultToCssClassConverter} class.
 *
 * @author Vaclav Stengl
 */
public class StepResultToCssClassConverterTest {

    private StepResultToCssClassConverter converter = new StepResultToCssClassConverter();

    /**
     * Step result converted to css class correctly.
     */
    @Test
    public void testConvertPassed() {
        assertEquals("success", converter.convert(SampleData.createStepResult(StepStatus.PASSED)));
    }

    /**
     * Step result converted to css class correctly.
     */
    @Test
    public void testConvertFailed() {
        assertEquals("danger", converter.convert(SampleData.createStepResult(StepStatus.FAILED)));
    }

    /**
     * Step result converted to css class correctly.
     */
    @Test
    public void testConvertSkipped() {
        assertEquals("default", converter.convert(SampleData.createStepResult(StepStatus.SKIPPED)));
    }

    /**
     * Step result converted to css class correctly.
     */
    @Test
    public void testConvertOther() {
        Stream.of(StepStatus.UNDEFINED, StepStatus.PENDING, StepStatus.MISSING).forEach(status ->
            assertEquals("danger", converter.convert(SampleData.createStepResult(status)))
        );
    }
}