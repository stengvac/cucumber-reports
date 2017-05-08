package cz.airbank.cucumber.reports.view.service;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

/**
 * Utils service for JSF core operations.
 *
 * @author David Passler
 */
@Component
public class JSFUtilsService {

    /**
     * Obtains JSF page parameter with specified name.
     *
     * @param parameterName the parameter name
     * @return String parameter value or null when no param is specified.
     */
    public String getPageParameter(String parameterName) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parameterName);
    }

    /**
     * Obtain locale used in browser.
     */
    public Locale returnBrowserLocale() {
        return FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }

    /**
     * Post new global warning global message.
     *
     * @param summary message summary
     */
    public void postWarningMessage(String summary) {
        postMessage(new FacesMessage(FacesMessage.SEVERITY_WARN, summary, ""));
    }

    /**
     * Post new global info global message.
     *
     * @param summary message summary
     */
    public void postInfoMessage(String summary) {
        postMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, summary, ""));
    }

    /**
     * Post provided message.
     */
    private void postMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
