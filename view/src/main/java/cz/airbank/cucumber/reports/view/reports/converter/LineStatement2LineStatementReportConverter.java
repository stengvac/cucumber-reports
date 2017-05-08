package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.ListConverter;
import cz.airbank.cucumber.reports.dao.entity.LineStatement;
import cz.airbank.cucumber.reports.view.reports.model.feature.LineStatementReport;

/**
 * Converter for line statements.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface LineStatement2LineStatementReportConverter extends ListConverter<LineStatement, LineStatementReport> {

    @Override
    LineStatementReport convert(LineStatement source);
}
