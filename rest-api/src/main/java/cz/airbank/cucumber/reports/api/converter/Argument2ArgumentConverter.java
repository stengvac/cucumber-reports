package cz.airbank.cucumber.reports.api.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.transport.model.Argument;

/**
 * Convert transport object to dao entity.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface Argument2ArgumentConverter extends Converter<Argument, cz.airbank.cucumber.reports.dao.entity.Argument> {

}
