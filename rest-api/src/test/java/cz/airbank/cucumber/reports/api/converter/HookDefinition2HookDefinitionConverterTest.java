package cz.airbank.cucumber.reports.api.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.transport.model.Argument;
import cz.airbank.cucumber.reports.dao.entity.HookDefinition;

/**
 * Tests for {@link HookDefinition2HookDefinitionConverter}
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class HookDefinition2HookDefinitionConverterTest {

    private static final String LOCATION = "loc";

    @Mock
    private Argument2ArgumentConverter argumentConverter;

    @InjectMocks
    private HookDefinition2HookDefinitionConverter converter = new HookDefinition2HookDefinitionConverterImpl();

    /**
     * Convert was performed successfully.
     */
    @Test
    public void testConvert_success() {
        cz.airbank.cucumber.reports.transport.model.HookDefinition hookDefinitionDefinition = new cz.airbank.cucumber.reports.transport.model.HookDefinition();
        Argument argument = new Argument();
        hookDefinitionDefinition.setLocation(LOCATION);
        hookDefinitionDefinition.setArguments(Collections.singletonList(argument));

        when(argumentConverter.convert(argument)).thenReturn(new cz.airbank.cucumber.reports.dao.entity.Argument());

        HookDefinition convertedHookDefinition = converter.convert(hookDefinitionDefinition);

        assertEquals(LOCATION, convertedHookDefinition.getLocation());
        assertEquals(1, convertedHookDefinition.getArguments().size());
    }
}