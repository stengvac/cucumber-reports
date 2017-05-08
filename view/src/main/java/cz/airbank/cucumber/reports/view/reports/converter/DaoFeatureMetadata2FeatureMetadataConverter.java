package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.DaoFeatureMetadata;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadata;

/**
 * Convert DAO entity {@link DaoFeatureMetadata} to transport object {@link FeatureMetadata}.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface DaoFeatureMetadata2FeatureMetadataConverter extends Converter<DaoFeatureMetadata, FeatureMetadata> {

}
