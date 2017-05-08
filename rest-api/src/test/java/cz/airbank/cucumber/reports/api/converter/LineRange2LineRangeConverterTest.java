package cz.airbank.cucumber.reports.api.converter;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

import cz.airbank.cucumber.reports.api.SampleData;
import cz.airbank.cucumber.reports.dao.entity.LineRange;

/**
 * Test of {@link LineRange2LineRangeConverter} impl.
 *
 * @author Vaclav Stengl
 */
public class LineRange2LineRangeConverterTest {

    private LineRange2LineRangeConverter testedConverted = new LineRange2LineRangeConverterImpl();

    /**
     * Test whenever converter work properly.
     */
    @Test
    public void testConvert_notNullInput() {
        LineRange converted = testedConverted.convert(SampleData.createLineRange());

        assertEquals(SampleData.LINE_RANGE_FIRST, converted.getFirst());
        assertEquals(SampleData.LINE_RANGE_LAST, converted.getLast());
    }
}