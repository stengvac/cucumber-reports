package cz.airbank.cucumber.reports.api.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import cz.airbank.cucumber.reports.transport.model.validation.ErrorTo;

/**
 * Base exception class for exceptions with errors.
 *
 * @author Vaclav Stengl
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public abstract class RestException extends RuntimeException {

    private List<ErrorTo> errorTos;

    public RestException(List<ErrorTo> errorTos) {
        this.errorTos = errorTos;
    }

    /**
     * List with validation errors.
     */
    public List<ErrorTo> getErrorTos() {
        if (errorTos == null) {
            errorTos = new ArrayList<>();
        }

        return errorTos;
    }

    public void setErrorTos(List<ErrorTo> errorTos) {
        this.errorTos = errorTos;
    }

    /**
     * The code used to create error when {@link #getErrorTos()} is empty.
     */
    public abstract String getCode();
}
