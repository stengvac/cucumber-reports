package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadataWithId;

/**
 * Converter from build run dao entity to build run presentation.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = DaoBuildRunMetadata2BuildRunMetadataConverter.class)
public interface BuildRun2BuildRunMetadataWithIdConverter extends Converter<BuildRun, BuildRunMetadataWithId> {

}
