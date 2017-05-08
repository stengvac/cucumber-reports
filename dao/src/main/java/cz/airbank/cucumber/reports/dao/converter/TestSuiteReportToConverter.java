package cz.airbank.cucumber.reports.dao.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.to.TestSuiteReportTo;

/**
 * Convert DAO entities to test suite report transfer object.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
    FeatureRun2FeatureMetadataWithIdToConverter.class,
    TestSuiteExecution2TestSuiteMetadataWithIdToConverter.class
})
public interface TestSuiteReportToConverter {

    @Mappings({
        @Mapping(source = "testSuiteExecution.featureRuns", target = "featureMetadataWithIdTos"),
        @Mapping(source = "testSuiteExecution", target = "testSuiteMetadataWithIdTo")
    })
    TestSuiteReportTo convert(TestSuiteExecution testSuiteExecution);
}
