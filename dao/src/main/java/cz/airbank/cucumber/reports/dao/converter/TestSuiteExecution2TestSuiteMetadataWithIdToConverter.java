package cz.airbank.cucumber.reports.dao.converter;

import org.mapstruct.Mapper;

import cz.airbank.cucumber.reports.common.converter.Converter;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.to.TestSuiteMetadataWithIdTo;

/**
 * Converter for test suite execution 2 test suite metadata with id To.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface TestSuiteExecution2TestSuiteMetadataWithIdToConverter extends Converter<TestSuiteExecution, TestSuiteMetadataWithIdTo> {

}
