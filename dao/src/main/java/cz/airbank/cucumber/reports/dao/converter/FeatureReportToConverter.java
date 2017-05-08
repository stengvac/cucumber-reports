package cz.airbank.cucumber.reports.dao.converter;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.common.converter.BiConverter;
import cz.airbank.cucumber.reports.dao.entity.FeatureRun;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.to.BuildRunMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.FeatureReportTo;

/**
 * Conversion between dao test suite entity and feature report TO.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
    TestSuiteExecution2TestSuiteMetadataWithIdToConverter.class
})
public interface FeatureReportToConverter
        extends BiConverter<TestSuiteExecution, BuildRunMetadataWithIdTo, FeatureReportTo> {

    @Mappings({
        @Mapping(source = "execution", target = "testSuiteMetadataWithIdTo"),
        @Mapping(source = "execution.featureRuns", target = "featureRun")
    })
    FeatureReportTo convert(TestSuiteExecution execution, BuildRunMetadataWithIdTo buildRunMetadataWithIdTo);

    /**
     * Execution found by query have exactly 1 record in feature run list or whole execution is null
     * so its ok to get first element.
     *
     * @param featureRuns the data source
     */
    default FeatureRun mapFeatureRun(List<FeatureRun> featureRuns) {
        return featureRuns.get(0);
    }
}
