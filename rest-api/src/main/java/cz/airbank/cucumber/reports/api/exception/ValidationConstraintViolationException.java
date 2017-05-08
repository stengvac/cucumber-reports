package cz.airbank.cucumber.reports.api.exception;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.util.List;

import org.springframework.web.bind.annotation.ResponseStatus;

import cz.airbank.cucumber.reports.transport.model.validation.ErrorTo;

/**
 * Exception used to propagate validation violations.
 *
 * @author Vaclav Stengl
 */
@ResponseStatus(UNPROCESSABLE_ENTITY)
public class ValidationConstraintViolationException extends RestException {

    private static final long serialVersionUID = 6361556295473039893L;

    /**
     * Code for constraint violation exception.
     */
    public static final String VALIDATION_CODE = "CONSTRAINT_VIOLATION";

    public ValidationConstraintViolationException(List<ErrorTo> errorTos) {
        super(errorTos);
    }

    @Override
    public String getCode() {
        return VALIDATION_CODE;
    }
}
