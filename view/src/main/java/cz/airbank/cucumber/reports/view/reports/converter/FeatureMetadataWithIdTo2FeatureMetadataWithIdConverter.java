package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.to.FeatureMetadataWithIdTo;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadataWithId;

/**
 * Converter between transport and view feature metadata with id object
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = {
        DaoFeatureMetadata2FeatureMetadataConverter.class
})
public interface FeatureMetadataWithIdTo2FeatureMetadataWithIdConverter extends Converter<FeatureMetadataWithIdTo, FeatureMetadataWithId>{

}
