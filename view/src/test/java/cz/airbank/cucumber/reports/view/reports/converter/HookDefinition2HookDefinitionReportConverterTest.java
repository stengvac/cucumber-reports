package cz.airbank.cucumber.reports.view.reports.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.view.SampleData;
import cz.airbank.cucumber.reports.view.reports.model.feature.Argument;
import cz.airbank.cucumber.reports.view.reports.model.feature.HookDefinitionReport;

/**
 * Test for {@link HookDefinition2HookDefinitionReportConverter} class.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class HookDefinition2HookDefinitionReportConverterTest {

    @Mock
    private Argument2ArgumentConverter argumentConverter;

    @InjectMocks
    private HookDefinition2HookDefinitionReportConverter converter
            = new HookDefinition2HookDefinitionReportConverterImpl();

    /**
     * Test successful conversion.
     */
    @Test
    public void testConvert_success() {
        Argument convertedArg = new Argument();

        when(argumentConverter.convert(SampleData.ARGUMENT)).thenReturn(convertedArg);

        HookDefinitionReport converted = converter.convert(SampleData.createHookDefinition());

        assertEquals(SampleData.LOCATION, converted.getLocation());
        assertEquals(1, converted.getArguments().size());
        assertSame(convertedArg, converted.getArguments().get(0));
    }
}