package cz.airbank.cucumber.reports.view.reports.converter;

import java.util.function.Function;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.to.BuildRunWithTestSuitesTo;
import cz.airbank.cucumber.reports.view.model.BuildRunWithCounts;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadata;

/**
 * Perform conversion between DAO TO and view object.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
    BuildRunMetadataWithIdTo2BuildRunMetadataWithIdConverter.class,
    TestSuiteWithFeaturesMetadataTo2TestSuiteWithFeaturesMetadataConverter.class
})
public interface BuildRunWithTestSuitesTo2BuildRunWithCountsMapper extends Converter<BuildRunWithTestSuitesTo, BuildRunWithCounts> {

    @Mappings({
        @Mapping(source = "buildRunMetadataWithIdTo", target = "metadataWithId"),
        @Mapping(source = "testSuiteWithFeaturesMetadataToes", target = "testSuiteWithCountsList"),
        @Mapping(ignore = true, target = "scenarioExecutionsTotal"),
        @Mapping(ignore = true, target = "scenarioExecutionsPassed")
    })
    @Override
    BuildRunWithCounts convert(BuildRunWithTestSuitesTo source);

    @AfterMapping
    default void addTotalCounts(@MappingTarget BuildRunWithCounts buildRunWithCounts) {
        //sum of sums, where function provided as param is source of data
        Function<Function<FeatureMetadata, Integer>, Integer> sumOf = (methodToGetData) ->
            buildRunWithCounts.getTestSuiteWithCountsList().stream().mapToInt(
                testSuiteWithFeaturesMetadata -> testSuiteWithFeaturesMetadata.getFeatureMetadataList().stream().mapToInt(
                    methodToGetData::apply
                ).sum()
            ).sum();

        int testExecutionsPassed = sumOf.apply(FeatureMetadata::getScenarioExecutionsPassed);
        int testExecutionsTotal = sumOf.apply(FeatureMetadata::getScenarioExecutionsTotal);

        buildRunWithCounts.setScenarioExecutionsPassed(testExecutionsPassed);
        buildRunWithCounts.setScenarioExecutionsTotal(testExecutionsTotal);
    }
}
