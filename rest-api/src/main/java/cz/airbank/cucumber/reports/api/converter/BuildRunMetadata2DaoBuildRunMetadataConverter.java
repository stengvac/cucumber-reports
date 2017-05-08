package cz.airbank.cucumber.reports.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.DaoBuildRunMetadata;
import cz.airbank.cucumber.reports.transport.model.BuildRunMetadata;

/**
 * Perform conversion between build run metadata TO and DAO entity.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface BuildRunMetadata2DaoBuildRunMetadataConverter extends Converter<BuildRunMetadata, DaoBuildRunMetadata> {

    @Mapping(ignore = true, target = "passed")
    @Override
    DaoBuildRunMetadata convert(BuildRunMetadata source);
}
