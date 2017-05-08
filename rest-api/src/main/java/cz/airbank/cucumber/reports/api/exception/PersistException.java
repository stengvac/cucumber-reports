package cz.airbank.cucumber.reports.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception used propagate DAO persist errors.
 *
 * @author Vaclav Stengl
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class PersistException extends RuntimeException {

    private static final long serialVersionUID = 5981908928484461703L;

    public PersistException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
