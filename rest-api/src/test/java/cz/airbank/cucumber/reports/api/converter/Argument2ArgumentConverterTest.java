package cz.airbank.cucumber.reports.api.converter;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.transport.model.Argument;

/**
 * Tests for {@link Argument2ArgumentConverter}
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class Argument2ArgumentConverterTest {

    private static final String ARG_NAME = "name";
    private static final int OFFSET = 4;

    private Argument2ArgumentConverter converter = new Argument2ArgumentConverterImpl();

    /**
     * Conversion performed successfully.
     */
    @Test
    public void testConvert_success() {
        Argument argument = new Argument();
        argument.setArgumentValue(ARG_NAME);
        argument.setOffset(OFFSET);

        cz.airbank.cucumber.reports.dao.entity.Argument converted =  converter.convert(argument);

        assertEquals(ARG_NAME, converted.getArgumentValue());
        assertEquals(OFFSET, converted.getOffset());
    }
}