package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.StepDefinition;
import cz.airbank.cucumber.reports.view.reports.model.feature.StepDefinitionReport;

/**
 * Converter between step definition in DAO layer and step definition report in view layer.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
        RowTableDefinition2DataTableConverter.class,
        Argument2ArgumentConverter.class
})
public interface StepDefinition2StepDefinitionReportMapper extends Converter<StepDefinition, StepDefinitionReport> {

    @Mappings({
            @Mapping(ignore = true, target = "keyword"),
            @Mapping(ignore = true, target = "stepDefinition")
    })
    @Override
    StepDefinitionReport convert(StepDefinition source);

    /**
     * Split step name to key word and step definition.
     * This operation is needed, because keywords in english (@Given, @And, @But, @When, @Then) used in .feature file
     * are lang dependent. So in czech you can use localized (@A, ...) with diacritics.............................
     *
     * @param stepDefinitionReport the target to set transformed step definition
     * @param stepDefinition       the data source
     */
    @AfterMapping
    default void afterMapping(@MappingTarget StepDefinitionReport stepDefinitionReport, StepDefinition stepDefinition) {
        int keywordIndex = stepDefinition.getName().indexOf(" ");
        stepDefinitionReport.setKeyword(stepDefinition.getName().substring(0, keywordIndex));
        stepDefinitionReport.setStepDefinition(stepDefinition.getName().substring(keywordIndex + 1));
    }
}
