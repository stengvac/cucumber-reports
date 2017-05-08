package cz.airbank.cucumber.reports.view.reports.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import cz.airbank.cucumber.reports.common.converter.BiConverter;
import cz.airbank.cucumber.reports.dao.to.BuildRunMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.FeatureMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteReportTo;
import cz.airbank.cucumber.reports.view.reports.service.model.FeaturesInModule;
import cz.airbank.cucumber.reports.view.reports.service.model.TestSuiteReport;

/**
 * Converter of test suite report transfer object to view object.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
    BuildRunMetadataWithIdTo2BuildRunMetadataWithIdConverter.class,
    TestSuiteMetadataWithIdTo2TestSuiteMetadataWithIdConverter.class
})
public abstract class TestSuiteReportTo2TestSuiteReportConverter implements BiConverter<TestSuiteReportTo, BuildRunMetadataWithIdTo, TestSuiteReport> {

    @Autowired
    private FeatureMetadataWithIdTo2FeatureMetadataWithIdConverter featureMetadataWithIdConverter;

    @Mappings({
        @Mapping(source = "buildRunMetadataWithIdTo", target = "buildRunMetadataWithId"),
        @Mapping(source = "testSuiteReportTo.testSuiteMetadataWithIdTo", target = "testSuiteMetadataWithId"),
        @Mapping(source = "testSuiteReportTo.featureMetadataWithIdTos", target = "featuresInModules")
    })
    @Override
    public abstract TestSuiteReport convert(TestSuiteReportTo testSuiteReportTo, BuildRunMetadataWithIdTo buildRunMetadataWithIdTo);

    /**
     * Perform mapping between features in test suite and their view representation.
     *
     * @param featureMetadataWithIdTos to convert
     * @return converted features with metadata and id
     */
    protected List<FeaturesInModule> convertFeatureInModules(List<FeatureMetadataWithIdTo> featureMetadataWithIdTos) {
        Map<String, FeaturesInModule> featuresInModuleMap = new HashMap<>();
        for (FeatureMetadataWithIdTo featureMetadataWithIdTo : featureMetadataWithIdTos) {

            String module = featureMetadataWithIdTo.getMetadata().getModule();
            if (!featuresInModuleMap.containsKey(module)) {
                FeaturesInModule featuresInModule = new FeaturesInModule();
                featuresInModule.setModule(module);
                featuresInModuleMap.put(module, featuresInModule);
            }

            FeaturesInModule featuresInModule = featuresInModuleMap.get(module);

            //for declared abstract method mapstruct generate code instead of using provided converter
            featuresInModule.getFeaturesWithId().add(featureMetadataWithIdConverter.convert(featureMetadataWithIdTo));
        }

        return new ArrayList<>(featuresInModuleMap.values());
    }
}
