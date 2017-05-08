package cz.airbank.cucumber.reports.view.exceptionhandling;

import javax.faces.context.ExceptionHandler;

import org.primefaces.application.exceptionhandler.PrimeExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jsf exception (also AJAX exception) handler. This handler extends {@link PrimeExceptionHandler}
 * implementation and redefine used {@link Logger}.
 *
 * @author Vaclav Stengl
 */
public class CucumberExceptionHandler extends PrimeExceptionHandler {

    private static final Logger sLog = LoggerFactory.getLogger(CucumberExceptionHandler.class);

    public CucumberExceptionHandler(ExceptionHandler wrapped) {
        super(wrapped);
    }

    @Override
    protected void logException(Throwable rootCause) {
        sLog.debug("", rootCause);
    }
}
