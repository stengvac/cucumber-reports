package cz.airbank.cucumber.reports.view.reports.converter;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.to.TestSuiteWithFeaturesMetadataTo;
import cz.airbank.cucumber.reports.view.model.TestSuiteWithFeaturesMetadata;

/**
 * Converter between DAO To and view object with information about executed features.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
    TestSuiteMetadataWithIdTo2TestSuiteMetadataWithIdConverter.class,
    DaoFeatureMetadata2FeatureMetadataConverter.class
})
public interface TestSuiteWithFeaturesMetadataTo2TestSuiteWithFeaturesMetadataConverter
        extends Converter<TestSuiteWithFeaturesMetadataTo, TestSuiteWithFeaturesMetadata> {

    @Mappings({
        @Mapping(source = "testSuiteMetadataWithIdTo", target = "testSuiteMetadataWithId")
    })
    @Override
    TestSuiteWithFeaturesMetadata convert(TestSuiteWithFeaturesMetadataTo source);
}
