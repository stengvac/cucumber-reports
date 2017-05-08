package cz.airbank.cucumber.reports.api.converter;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.api.SampleData;
import cz.airbank.cucumber.reports.dao.entity.RowData;
import cz.airbank.cucumber.reports.dao.entity.RowTableDefinition;
import cz.airbank.cucumber.reports.transport.model.DataTable;
import cz.airbank.cucumber.reports.transport.model.Statement;

/**
 * Tests for {@link DataTable2RowTableDefinitionConverter} impl.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class DataTable2RowTableDefinitionConverterTest extends CommonAttributesTestHelper {

    @InjectMocks
    private DataTable2RowTableDefinitionConverter converter = new DataTable2RowTableDefinitionConverterImpl();

    private DataTable dataTable = SampleData.createDataTable();

    @Override
    protected Statement returnStatementToConvert() {
        return dataTable;
    }

    @Test
    public void testConvert_notNullInput() {
        RowTableDefinition converted = converter.convert(dataTable);

        assertTaggedStatement(converted);
        assertEquals(SampleData.ROW_TABLE_HEADER_DESCRIPTION_COL, converted.getTableHeader().getDescription());
        assertTrue(SampleData.ROW_TABLE_HEADERS.containsAll(converted.getTableHeader().getData()));

        assertEquals(1, converted.getRowData().size());
        RowData rowData = converted.getRowData().get(0);
        assertEquals(SampleData.ROW_TABLE_DATA_VAL, rowData.getData());
        assertEquals(SampleData.ROW_TABLE_DESCRIPTION_VAL, rowData.getDescription());
    }

    @Test
    public void testConvertDataRow_notNullInput() {
        RowData rowData = converter.convertDataRow(SampleData.createDataRow());

        assertTrue(SampleData.ROW_TABLE_DATA_VAL.containsAll(rowData.getData()));
        assertEquals(SampleData.ROW_TABLE_DESCRIPTION_VAL, rowData.getDescription());
    }

}