package cz.airbank.cucumber.reports.api.converter;

import java.util.Collections;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.common.converter.ListConverter;
import cz.airbank.cucumber.reports.dao.entity.RowData;
import cz.airbank.cucumber.reports.dao.entity.RowTableDefinition;
import cz.airbank.cucumber.reports.transport.model.DataRow;
import cz.airbank.cucumber.reports.transport.model.DataTable;

/**
 * Perform conversion between data table TO and DAO entity.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
        LineStatement2LineStatementConverter.class,
        LineRange2LineRangeConverter.class
})
public interface DataTable2RowTableDefinitionConverter extends ListConverter<DataTable, RowTableDefinition> {

    @Mappings({
        @Mapping(source = "headers", target = "tableHeader"),
        @Mapping(source = "dataRowList", target = "rowData")
    })
    @Override
    RowTableDefinition convert(DataTable dataTable);

    /**
     * Temp method for conversion from one element to list until list of examples are fully supported.
     *
     * @param dataTable to convert
     * @return empty list for null
     */
    default List<RowTableDefinition> oneToList(DataTable dataTable) {
        if (dataTable == null) {
            return Collections.emptyList();
        }

        return convertList(Collections.singletonList(dataTable));
    }

    /**
     * Convert transport data row to dao entity.
     */
    @Mappings({
        @Mapping(source = "valueList", target = "data"),
        @Mapping(source = "descriptionColumn", target = "description")
    })
    RowData convertDataRow(DataRow dataRow);
}
