package cz.airbank.cucumber.reports.view.reports.converter;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.airbank.cucumber.reports.dao.entity.Argument;

/**
 * Test for {@link Argument2ArgumentConverter}.
 *
 * @author Vaclav Stengl
 */
public class Argument2ArgumentConverterTest {

    private static final int OFFSET = 1;
    private static final String ARG_VAL = "val";

    private Argument2ArgumentConverter converter = new Argument2ArgumentConverterImpl();

    /**
     * Test of successful conversion.
     */
    @Test
    public void testConvert_success() {
        Argument argument = new Argument();
        argument.setOffset(OFFSET);
        argument.setArgumentValue(ARG_VAL);

        cz.airbank.cucumber.reports.view.reports.model.feature.Argument converted = converter.convert(argument);

        assertEquals(OFFSET, converted.getOffset());
        assertEquals(ARG_VAL, converted.getArgumentValue());
    }
}