package cz.airbank.cucumber.reports.dao.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.DaoFeatureMetadata;
import cz.airbank.cucumber.reports.dao.entity.FeatureRun;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.to.TestSuiteWithFeaturesMetadataTo;

/**
 * Perform conversion between DAO entity and
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {TestSuiteExecution2TestSuiteMetadataWithIdToConverter.class})
public interface TestSuiteExecution2TestSuiteWithFeaturesMetadataToConverter extends Converter<TestSuiteExecution, TestSuiteWithFeaturesMetadataTo> {

    @Mappings({
        @Mapping(source = "featureRuns", target = "featureMetadataList"),
        @Mapping(source = "source", target = "testSuiteMetadataWithIdTo")
    })
    @Override
    TestSuiteWithFeaturesMetadataTo convert(TestSuiteExecution source);

    /**
     * Extract feature metadata from feature run.
     *
     * @param featureRuns the data source
     * @return extracted metadata
     */
    default List<DaoFeatureMetadata> mapFeatureRuns(List<FeatureRun> featureRuns) {
        return featureRuns.stream().map(
            FeatureRun::getMetadata
        ).collect(Collectors.toList());
    }
}
