package cz.airbank.cucumber.reports.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.DaoTestSuiteMetadata;
import cz.airbank.cucumber.reports.transport.model.TestSuiteMetadata;

/**
 * Perform conversion between transport and DAO entity test suite metadata.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface TestSuiteMetadata2DaoTestSuiteMetadataConverter extends Converter<TestSuiteMetadata, DaoTestSuiteMetadata> {

    @Mapping(ignore = true, target = "passed")
    @Override
    DaoTestSuiteMetadata convert(TestSuiteMetadata source);
}
