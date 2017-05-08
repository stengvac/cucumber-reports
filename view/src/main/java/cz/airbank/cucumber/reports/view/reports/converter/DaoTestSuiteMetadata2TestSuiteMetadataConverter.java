package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.DaoTestSuiteMetadata;
import cz.airbank.cucumber.reports.view.reports.model.testsuite.TestSuiteMetadata;
import cz.airbank.cucumber.reports.view.reports.model.testsuite.TestSuiteMetadataWithId;

/**
 * Convert DAO entity {@link DaoTestSuiteMetadata} to transport object {@link TestSuiteMetadataWithId}.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface DaoTestSuiteMetadata2TestSuiteMetadataConverter extends Converter<DaoTestSuiteMetadata, TestSuiteMetadata> {

}
