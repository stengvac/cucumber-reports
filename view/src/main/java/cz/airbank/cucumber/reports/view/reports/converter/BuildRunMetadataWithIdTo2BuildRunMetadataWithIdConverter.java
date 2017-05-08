package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.to.BuildRunMetadataWithIdTo;
import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadataWithId;

/**
 * Conversion between TO and view objects.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = DaoBuildRunMetadata2BuildRunMetadataConverter.class)
public interface BuildRunMetadataWithIdTo2BuildRunMetadataWithIdConverter extends Converter<BuildRunMetadataWithIdTo, BuildRunMetadataWithId> {
}
