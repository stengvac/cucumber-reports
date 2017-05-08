package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.DaoBuildRunMetadata;
import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadata;

/**
 * Convert DAO entity {@link DaoBuildRunMetadata} to transport object {@link BuildRunMetadata}.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface DaoBuildRunMetadata2BuildRunMetadataConverter extends Converter<DaoBuildRunMetadata, BuildRunMetadata> {
}
