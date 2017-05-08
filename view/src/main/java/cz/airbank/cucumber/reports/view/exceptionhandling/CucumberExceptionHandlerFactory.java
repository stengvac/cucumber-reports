package cz.airbank.cucumber.reports.view.exceptionhandling;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Provide exception (also AJAX exception) handler instance.
 *
 * @author Vaclav Stengl
 */
public class CucumberExceptionHandlerFactory extends ExceptionHandlerFactory {

    private final ExceptionHandlerFactory wrapped;

    public CucumberExceptionHandlerFactory(final ExceptionHandlerFactory wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return new CucumberExceptionHandler(wrapped.getExceptionHandler());
    }

    @Override
    public ExceptionHandlerFactory getWrapped() {
        return wrapped;
    }
}
