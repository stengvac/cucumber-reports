package cz.airbank.cucumber.reports.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.ScenarioExecution;
import cz.airbank.cucumber.reports.transport.model.ScenarioRun;

/**
 * Converter between transport and DAO entity scenario run.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = StepResult2StepRunConverter.class)
public interface ScenarioRun2ScenarioExecutionConverter extends Converter<ScenarioRun, ScenarioExecution> {

    @Mappings({
        @Mapping(source = "beforeHookResults", target = "beforeHookRuns"),
        @Mapping(source = "backgroundStepResults", target = "backgroundStepRuns"),
        @Mapping(source = "scenarioStepResults", target = "scenarioStepRuns"),
        @Mapping(source = "afterHookResults", target = "afterHookRuns")
    })
    @Override
    ScenarioExecution convert(ScenarioRun source);
}
