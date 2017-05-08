package cz.airbank.cucumber.reports.api.converter;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.airbank.cucumber.reports.api.SampleData;
import cz.airbank.cucumber.reports.dao.entity.LineStatement;

/**
 * Tests for {@link LineStatement2LineStatementConverter} impl.
 *
 * @author Vaclav Stengl
 */
public class LineStatement2LineStatementConverterTest {

    private LineStatement2LineStatementConverter testedConverter = new LineStatement2LineStatementConverterImpl();

    /**
     * Test whenever converter work properly.
     */
    @Test
    public void testConversion_notNullInput() {
        LineStatement converted = testedConverter.convert(SampleData.createLineStatement());

        assertEquals(SampleData.LINE_STATEMENT_LINE, converted.getLine());
        assertEquals(SampleData.LINE_STATEMENT_VAL, converted.getStatement());
    }
}