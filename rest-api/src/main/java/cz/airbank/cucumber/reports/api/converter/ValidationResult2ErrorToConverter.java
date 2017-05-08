package cz.airbank.cucumber.reports.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cz.airbank.cucumber.reports.api.validation.ValidationConstraintViolation;
import cz.airbank.cucumber.reports.common.converter.ListConverter;
import cz.airbank.cucumber.reports.transport.model.validation.ErrorTo;

/**
 * Perform conversion between validation result and transport object for validation results.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface ValidationResult2ErrorToConverter extends ListConverter<ValidationConstraintViolation, ErrorTo> {

    @Mapping(ignore = true, target = "message")
    @Override
    ErrorTo convert(ValidationConstraintViolation source);
}
