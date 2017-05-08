package cz.airbank.cucumber.reports.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.util.Assert;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.common.model.ScenarioType;
import cz.airbank.cucumber.reports.transport.model.ScenarioDefinition;

/**
 * Converter between transport and DAO entity for scenario definition.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
        DataTable2RowTableDefinitionConverter.class,
        StepDefinition2StepDefinitionConverter.class,
        ScenarioRun2ScenarioExecutionConverter.class,
        HookDefinition2HookDefinitionConverter.class,
        LineStatement2LineStatementConverter.class,
        LineRange2LineRangeConverter.class
})
public interface ScenarioDefinition2ScenarioDefinitionConverter extends Converter<ScenarioDefinition, cz.airbank.cucumber.reports.dao.entity.ScenarioDefinition> {

    @Mappings({
            @Mapping(source = "beforeHooks", target = "beforeHookDefinitions"),
            @Mapping(source = "scenarioRunList", target = "scenarioExecutions"),
            @Mapping(source = "stepDefinitionList", target = "stepDefinitions"),
            @Mapping(source = "afterHooks", target = "afterHookDefinitions")
    })

    @Override
    cz.airbank.cucumber.reports.dao.entity.ScenarioDefinition convert(ScenarioDefinition source);

    /**
     * Convert scenario type used in transport model in rest of application.
     *
     * @param type to convert
     * @return converted value
     * @throws IllegalArgumentException when input is null - this should not happen
     */
    default ScenarioType convertScenarioType(cz.airbank.cucumber.reports.transport.model.ScenarioType type) {
        Assert.notNull(type);

        return ScenarioType.valueOf(type.name());
    }
}
