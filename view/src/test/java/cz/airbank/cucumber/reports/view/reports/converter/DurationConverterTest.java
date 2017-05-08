package cz.airbank.cucumber.reports.view.reports.converter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.view.jsfconverter.DurationConverter;

/**
 * Tests for {@link DurationConverter}.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class DurationConverterTest {

    private DurationConverter converter;

    @Before
    public void setUp() {
        converter = new DurationConverter();
    }

    /**
     * No need to get duration as string
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testGetAsObject() throws Exception {
        converter.getAsObject(null, null, null);
    }

    /**
     * Duration is converted to human readable format.
     */
    @Test
    public void testGetAsString() {
        assertEquals("00:05.000", converter.getAsString(null, null, 5_000_000_000L));
    }
}