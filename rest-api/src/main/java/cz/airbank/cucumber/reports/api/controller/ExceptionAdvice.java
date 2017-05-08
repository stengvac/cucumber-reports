package cz.airbank.cucumber.reports.api.controller;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import cz.airbank.cucumber.reports.api.exception.RestException;
import cz.airbank.cucumber.reports.transport.model.validation.ErrorListTo;
import cz.airbank.cucumber.reports.transport.model.validation.ErrorTo;

/**
 * Exception handling for rest api.
 *
 * @author Vaclav Stengl
 */
@RestControllerAdvice(annotations = RestController.class)
@Order(LOWEST_PRECEDENCE)
public class ExceptionAdvice {

    private static final Logger sLog = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     * Handle rest exceptions.
     *
     * @param exception to handle
     * @return response body with validation constraints trespasses
     */
    @ExceptionHandler(RestException.class)
    @ResponseBody
    public ErrorListTo handleRestException(RestException exception) {
        sLog.debug("", exception);

        List<ErrorTo> errorTos = exception.getErrorTos();
        if (CollectionUtils.isEmpty(errorTos)) {
            ErrorTo errorTo = new ErrorTo(exception.getCode(), exception.getMessage());
            errorTos = Collections.singletonList(errorTo);
        }

        ErrorListTo errorListTo = new ErrorListTo();
        errorListTo.setErrorTos(errorTos);

        return errorListTo;
    }

    /**
     * Handle unrecognized property exception thrown by Jackson framework when JSON property can't be mapped to POJO.
     *
     * @param exception to handle
     * @return POJO with errors
     */
    @ExceptionHandler(UnrecognizedPropertyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListTo handleUnrecognizedPropertyException(UnrecognizedPropertyException exception) {
        sLog.debug("", exception);

        String attribute = buildPathToJsonAttribute(exception.getPath());
        String code = "UNPROCESSABLE_PROPERTY";
        String message = "One of JSON properties was not recognized.";
        ErrorTo errorTo = new ErrorTo(attribute, code, message);

        ErrorListTo errorListTo = new ErrorListTo();
        errorListTo.setErrorTos(Collections.singletonList(errorTo));

        return errorListTo;
    }

    /**
     * Handle throwable.
     *
     * @param throwable the data source
     * @return POJO with errors
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorListTo handleThrowable(Throwable throwable) {
        sLog.debug("", throwable);

        String code = HttpStatus.INTERNAL_SERVER_ERROR.name();
        String message = throwable.getMessage();

        ErrorListTo errorListTo = new ErrorListTo();
        ErrorTo errorTo = new ErrorTo(code, message);
        errorListTo.setErrorTos(Collections.singletonList(errorTo));

        return errorListTo;
    }

    /**
     * Create full path to unrecognized JSON attribute.
     *
     * @param path to attribute
     * @return string representing full path to JSON attribute
     */
    private String buildPathToJsonAttribute(List<JsonMappingException.Reference> path) {
        StringBuilder builder = new StringBuilder();

        if (CollectionUtils.isEmpty(path)) {
            return "UNRECOGNIZED_PATH";
        }

        path.forEach(reference -> {
            builder.append(reference.getFieldName());

            if (reference.getIndex() != -1) {
                builder.append('[');
                builder.append(reference.getIndex());
                builder.append(']');
            }

            builder.append('.');
        });

        return builder.substring(0, builder.length() - 1);
    }
}
