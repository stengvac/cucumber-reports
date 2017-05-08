package cz.airbank.cucumber.reports.api.converter;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.transport.model.StepDefinition;

/**
 * Converter between transport and dao entity step definition.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
        DataTable2RowTableDefinitionConverter.class,
        LineStatement2LineStatementConverter.class,
        LineRange2LineRangeConverter.class,
        Argument2ArgumentConverter.class
})
public interface StepDefinition2StepDefinitionConverter extends Converter<StepDefinition, cz.airbank.cucumber.reports.dao.entity.StepDefinition> {

    /**
     * Conversion method for DAO step definition name.
     * Final name is created as {@link StepDefinition#getKeyword()} concatenated by whitespace with {@link StepDefinition#getName()}
     *
     * @param stepDefinition the data source{
     */
    @AfterMapping
    default void convertName(StepDefinition stepDefinition, @MappingTarget cz.airbank.cucumber.reports.dao.entity.StepDefinition target) {
        target.setName(stepDefinition.getKeyword() + " " + stepDefinition.getName());
    }
}
