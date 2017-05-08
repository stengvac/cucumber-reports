package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.dao.to.BuildRunMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.BuildRunWithTestSuitesTo;
import cz.airbank.cucumber.reports.view.reports.service.model.BuildRunReport;

/**
 * Dao entity to view object converter.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
        BuildRunMetadataWithIdTo2BuildRunMetadataWithIdConverter.class,
        TestSuiteWithFeaturesMetadataTo2TestSuiteWithFeaturesMetadataConverter.class
})
public interface BuildRunReportMapper {

    @Mappings({
            @Mapping(source = "source.testSuiteWithFeaturesMetadataToes", target = "testSuiteWithFeaturesMetadata"),
            @Mapping(source = "source.buildRunMetadataWithIdTo", target = "currentBuildRunWithId")
    })
    BuildRunReport convert(BuildRunWithTestSuitesTo source,
                           BuildRunMetadataWithIdTo previousBuildRunWithId,
                           BuildRunMetadataWithIdTo nextMetadataWithId);

}
