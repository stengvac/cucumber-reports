package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.ScenarioExecution;
import cz.airbank.cucumber.reports.dao.entity.StepRun;
import cz.airbank.cucumber.reports.view.reports.model.feature.ResultReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.HookDefinitionReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.ScenarioRunReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.StepDefinitionReport;

/**
 * From scenario definition convert all scenario run reports.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = Argument2ArgumentConverter.class)
public interface ScenarioExecution2ScenarioRunReportMapper extends Converter<ScenarioExecution, ScenarioRunReport> {

    @Mappings({
            @Mapping(source = "beforeHookRuns", target = "beforeHookStepResults"),
            @Mapping(source = "backgroundStepRuns", target = "backgroundStepResults"),
            @Mapping(source = "scenarioStepRuns", target = "scenarioStepResults"),
            @Mapping(source = "afterHookRuns", target = "afterHookStepResults"),

            @Mapping(ignore = true, target = "runIndex"), //TODO probably useless attribute - check
            @Mapping(ignore = true, target = "report") //set later
    })
    @Override
    ScenarioRunReport convert(ScenarioExecution source);

    /**
     * Common method for conversion of raw types. Declare common mapping rules, which inherit other typed methods.
     */
    @Mappings({
            @Mapping(source = "embeddedIds", target = "embeddingList"),
            @Mapping(source = "stepStatus", target = "status"),
            @Mapping(ignore = true, target = "definitionReport")
    })
    ResultReport convertBaseResultReport(StepRun stepRun);

    /**
     * Converter for typed generic.
     *
     * @param stepRun the data source
     * @return converted object with type
     */
    @InheritConfiguration(name = "convertBaseResultReport")
    ResultReport<HookDefinitionReport> convertHookRes(StepRun stepRun);

    /**
     * Converter for typed generic.
     *
     * @param stepRun the data source
     * @return converted object with type
     */
    @InheritConfiguration(name = "convertBaseResultReport")
    ResultReport<StepDefinitionReport> convertStepRes(StepRun stepRun);
}

