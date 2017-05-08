package cz.airbank.cucumber.reports.view.reports.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.to.TestSuiteMetadataWithIdTo;
import cz.airbank.cucumber.reports.view.reports.model.testsuite.TestSuiteMetadataWithId;

/**
 * Converter between TO and view objects.
 *
 * @author Vaclav Stengl
 */
@Mapper(uses = DaoTestSuiteMetadata2TestSuiteMetadataConverter.class)
public interface TestSuiteMetadataWithIdTo2TestSuiteMetadataWithIdConverter extends Converter<TestSuiteMetadataWithIdTo, TestSuiteMetadataWithId> {

}
