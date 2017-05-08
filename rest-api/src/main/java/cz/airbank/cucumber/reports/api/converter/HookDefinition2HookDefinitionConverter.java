package cz.airbank.cucumber.reports.api.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.HookDefinition;

/**
 * Convert transport object to dao entity.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = Argument2ArgumentConverter.class)
public interface HookDefinition2HookDefinitionConverter extends Converter<cz.airbank.cucumber.reports.transport.model.HookDefinition, HookDefinition> {

}
