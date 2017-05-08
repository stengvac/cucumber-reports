package cz.airbank.cucumber.reports.view.reports.converter;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.view.SampleData;
import cz.airbank.cucumber.reports.view.reports.model.feature.DataRow;
import cz.airbank.cucumber.reports.view.reports.model.feature.DataTable;

/**
 * Tests for {@link RowTableDefinition2DataTableConverter} class.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class RowTableDefinition2DataTableListConverterTest {

    @Mock
    private LineStatement2LineStatementReportConverter lineStatementReportConverter;

    @InjectMocks
    private RowTableDefinition2DataTableConverter converter = new RowTableDefinition2DataTableConverterImpl();

    /**
     * Test conversion between data tables.
     */
    @Test
    public void testConvert() {
        DataTable dataTable = converter.convert(SampleData.createRowTableDefinition());

        assertNotNull(dataTable);
        //data table headers converted
        assertEquals(SampleData.ROW_TABLE_HEADER_DESCRIPTION_COL, dataTable.getHeaders().getDescriptionColumn());
        assertEquals(SampleData.ROW_TABLE_HEADERS, dataTable.getHeaders().getValueList());
        assertEquals(1, dataTable.getDataRowList().size());
        //data converted
        DataRow dataRow = dataTable.getDataRowList().get(0);
        assertEquals(SampleData.ROW_TABLE_DATA_VAL, dataRow.getValueList());
        assertEquals(SampleData.ROW_TABLE_DESCRIPTION_VAL, dataRow.getDescriptionColumn());
    }
}