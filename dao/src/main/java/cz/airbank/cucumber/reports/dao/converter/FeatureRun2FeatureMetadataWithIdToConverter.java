package cz.airbank.cucumber.reports.dao.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.FeatureRun;
import cz.airbank.cucumber.reports.dao.to.FeatureMetadataWithIdTo;

/**
 * Convert DAO feature run to transport object.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface FeatureRun2FeatureMetadataWithIdToConverter extends Converter<FeatureRun, FeatureMetadataWithIdTo> {

}
