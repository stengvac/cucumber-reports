package cz.airbank.cucumber.reports.view.exceptionhandling.bean;

import javax.faces.application.ProjectStage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;

/**
 * Support bean for error pages.
 *
 * @author Vaclav Stengl
 */
@Named
@Scope
public class ExceptionBean {

    /**
     * From faces context obtains URL of current request.
     */
    public String getRequestUrl(){
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        return req.getRequestURL().toString();
    }

    /**
     * From faces context retrieve referer URL.
     */
    public String getRefererUrl() {
        ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();

        return ext.getRequestHeaderMap().get("referer");
    }

    /**
     * Compare project stage defined in {@code faces-context.xml} with {@link ProjectStage#Development}.
     *
     * @return true when project is in development stage,
     *         false otherwise.
     */
    public boolean isInDevelopmentStage() {
        return ProjectStage.Development
            .equals(FacesContext.getCurrentInstance().getApplication().getProjectStage());
    }
}
