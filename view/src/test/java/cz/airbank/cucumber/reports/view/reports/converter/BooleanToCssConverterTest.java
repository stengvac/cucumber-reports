package cz.airbank.cucumber.reports.view.reports.converter;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for {@link BooleanToCssConverter}.
 *
 * @author Vaclav Stengl
 */
public class BooleanToCssConverterTest {

    private BooleanToCssConverter testedInstance = new BooleanToCssConverter();

    /**
     * True eq to {@link BooleanToCssConverter#TRUE_CSS}
     */
    @Test
    public void testConvertTrue() {
        assertEquals(BooleanToCssConverter.TRUE_CSS, testedInstance.convert(true));
    }

    /**
     * True eq to {@link BooleanToCssConverter#FALSE_CSS}
     */
    @Test
    public void testConvertFalse() {
        assertEquals(BooleanToCssConverter.FALSE_CSS, testedInstance.convert(false));
    }
}