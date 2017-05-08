package cz.airbank.cucumber.reports.api.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.transport.model.LineRange;

/**
 * Converter between transport object and DAO entity representing line range.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface LineRange2LineRangeConverter extends Converter<LineRange, cz.airbank.cucumber.reports.dao.entity.LineRange> {

}
