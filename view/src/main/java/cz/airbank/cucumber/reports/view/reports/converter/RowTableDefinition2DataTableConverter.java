package cz.airbank.cucumber.reports.view.reports.converter;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.common.converter.ListConverter;
import cz.airbank.cucumber.reports.dao.entity.RowData;
import cz.airbank.cucumber.reports.dao.entity.RowTableDefinition;
import cz.airbank.cucumber.reports.view.reports.model.feature.DataRow;
import cz.airbank.cucumber.reports.view.reports.model.feature.DataTable;

/**
 * Converter for data structures representing data tables inside feature files.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = LineStatement2LineStatementReportConverter.class)
public interface RowTableDefinition2DataTableConverter extends ListConverter<RowTableDefinition, DataTable> {

    @Mappings({
        @Mapping(source = "description", target = "descriptionColumn"),
        @Mapping(source = "data", target = "valueList")
    })
    DataRow convertRow(RowData rowData);

    @Mappings({
        @Mapping(source = "tableHeader", target = "headers"),
        @Mapping(source = "rowData", target = "dataRowList")
    })
    @Override
    DataTable convert(RowTableDefinition source);

    /**
     * Temporary conversion method until multiple example sections are fully supported.
     * TODO del when list of example sections are implemented
     *
     * @param rowTableDefinitions to convert from
     * @return null for empty list otherwise converted first element
     */
    default DataTable listToOne(List<RowTableDefinition> rowTableDefinitions) {
        List<DataTable> converted = convertList(rowTableDefinitions);

        if (converted.isEmpty()) {
            return null;
        }

        return converted.get(0);
    }
}
