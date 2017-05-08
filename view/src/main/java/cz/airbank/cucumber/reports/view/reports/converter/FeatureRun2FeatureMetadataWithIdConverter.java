package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.FeatureRun;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadataWithId;

/**
 * Converter between dao feature and view presentation of this entity.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = DaoFeatureMetadata2FeatureMetadataConverter.class)
public interface FeatureRun2FeatureMetadataWithIdConverter extends Converter<FeatureRun, FeatureMetadataWithId> {

}
