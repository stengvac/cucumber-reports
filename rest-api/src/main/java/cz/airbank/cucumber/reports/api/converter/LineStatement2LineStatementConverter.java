package cz.airbank.cucumber.reports.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.transport.model.LineStatement;

/**
 * Converter between TO and DAO entity for line statement.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface LineStatement2LineStatementConverter
        extends Converter<LineStatement, cz.airbank.cucumber.reports.dao.entity.LineStatement> {

    @Mapping(source = "value", target = "statement")
    @Override
    cz.airbank.cucumber.reports.dao.entity.LineStatement convert(LineStatement source);
}
