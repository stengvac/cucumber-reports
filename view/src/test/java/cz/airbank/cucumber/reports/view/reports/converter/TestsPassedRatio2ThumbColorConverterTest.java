package cz.airbank.cucumber.reports.view.reports.converter;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for {@link TestsPassedRatio2ThumbColorConverter}
 *
 * @author Vaclav Stengl
 */
public class TestsPassedRatio2ThumbColorConverterTest {

    private TestsPassedRatio2ThumbColorConverter converter = new TestsPassedRatio2ThumbColorConverter();

    /**
     * Input value >= 0.75
     */
    @Test
    public void testConvert_ratioGt0dot75() {
        assertEquals(TestsPassedRatio2ThumbColorConverter.THUMB_COLOR_OK, converter.convert(0.75));
    }

    /**
     * Input value > 0.25 && input < 0.75
     */
    @Test
    public void testConvert_ratioLt0dot75AndGt0dot25() {
        assertEquals(TestsPassedRatio2ThumbColorConverter.THUMB_COLOR_SOME_FAILED, converter.convert(0.74));
        assertEquals(TestsPassedRatio2ThumbColorConverter.THUMB_COLOR_SOME_FAILED, converter.convert(0.26));
    }

    /**
     * Input value < 0.25
     */
    @Test
    public void testConvert_ratioLt0dot25() {
        assertEquals(TestsPassedRatio2ThumbColorConverter.THUMB_COLOR_EPIC_FAIL, converter.convert(0.24));
    }
}