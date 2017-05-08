package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.ScenarioDefinition;
import cz.airbank.cucumber.reports.view.reports.model.feature.ScenarioDefinitionReport;

/**
 * Perform conversion between scenario definition and report.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
        RowTableDefinition2DataTableConverter.class,
        ScenarioExecution2ScenarioRunReportMapper.class,
        HookDefinition2HookDefinitionReportConverter.class,
        StepDefinition2StepDefinitionReportMapper.class,
        LineStatement2LineStatementReportConverter.class
})
public interface ScenarioDefinition2ScenarioDefinitionReportConverter extends Converter<ScenarioDefinition, ScenarioDefinitionReport> {

    @Mappings({
            @Mapping(source = "beforeHookDefinitions", target = "beforeHookDefinitionReports"),
            @Mapping(source = "afterHookDefinitions", target = "afterHookDefinitionReports"),
            @Mapping(source = "stepDefinitions", target = "stepDefinitionReports"),
            @Mapping(source = "scenarioExecutions", target = "scenarioRunReports"),
    })
    @Override
    ScenarioDefinitionReport convert(ScenarioDefinition source);
}