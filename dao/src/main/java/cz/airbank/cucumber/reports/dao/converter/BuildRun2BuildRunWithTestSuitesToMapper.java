package cz.airbank.cucumber.reports.dao.converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.to.BuildRunWithTestSuitesTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteWithFeaturesMetadataTo;

/**
 * Mapper between build run dao entity and map with test suites to transport object.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = BuildRun2BuildRunMetadataWithIdToConverter.class)
public interface BuildRun2BuildRunWithTestSuitesToMapper {

    @Mappings({
        @Mapping(source = "buildRun", target = "buildRunMetadataWithIdTo"),
        @Mapping(ignore = true, target = "testSuiteWithFeaturesMetadataToes")
    })
    BuildRunWithTestSuitesTo convert(BuildRun buildRun, Map<String, TestSuiteWithFeaturesMetadataTo> testSuiteWithFeaturesMap);

    /**
     * Map {@link TestSuiteWithFeaturesMetadataTo} to mapping target by {@link TestSuiteExecution#getId()}.
     *
     * @param buildRun provide ids to map on from test suite collection
     * @param testSuiteWithFeaturesMap the source of all mapped {@link TestSuiteWithFeaturesMetadataTo}
     * @param mappingTarget to set mapped data
     */
    @AfterMapping
    default void mapTestSuiteWithFeaturesMetadata(BuildRun buildRun,
                                                  Map<String, TestSuiteWithFeaturesMetadataTo> testSuiteWithFeaturesMap,
                                                  @MappingTarget BuildRunWithTestSuitesTo mappingTarget) {

        List<TestSuiteWithFeaturesMetadataTo> testSuiteWithFeatures = buildRun.getTestSuites().stream().map(
            (execution) -> testSuiteWithFeaturesMap.get(execution.getId())
        ).collect(Collectors.toList());

        mappingTarget.setTestSuiteWithFeaturesMetadataToes(testSuiteWithFeatures);
    }
}
