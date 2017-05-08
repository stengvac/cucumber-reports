package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.Argument;

/**
 * Map dao argument to view argument object.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface Argument2ArgumentConverter extends Converter<Argument, cz.airbank.cucumber.reports.view.reports.model.feature.Argument> {

    @Mapping(ignore = true, target = "diff")
    @Override
    cz.airbank.cucumber.reports.view.reports.model.feature.Argument convert(Argument source);
}
