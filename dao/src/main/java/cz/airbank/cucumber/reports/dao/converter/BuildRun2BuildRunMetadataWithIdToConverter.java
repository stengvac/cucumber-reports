package cz.airbank.cucumber.reports.dao.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.to.BuildRunMetadataWithIdTo;

/**
 * Perform conversion from build run to metadata.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface BuildRun2BuildRunMetadataWithIdToConverter extends Converter<BuildRun, BuildRunMetadataWithIdTo> {

}
