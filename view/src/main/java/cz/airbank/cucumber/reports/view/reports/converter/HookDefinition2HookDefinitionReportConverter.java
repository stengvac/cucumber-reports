package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.HookDefinition;
import cz.airbank.cucumber.reports.view.reports.model.feature.HookDefinitionReport;

/**
 * Convert DAO hook definition to view layer object.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
    Argument2ArgumentConverter.class
})
public interface HookDefinition2HookDefinitionReportConverter extends Converter<HookDefinition, HookDefinitionReport> {

}
