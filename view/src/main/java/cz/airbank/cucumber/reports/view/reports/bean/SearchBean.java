package cz.airbank.cucumber.reports.view.reports.bean;

import java.text.MessageFormat;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadataWithId;
import cz.airbank.cucumber.reports.view.reports.service.ReportsService;
import cz.airbank.cucumber.reports.view.service.I18nService;

/**
 * Backing bean which allow user to search builds by query
 *
 * @author Vaclav Stengl
 */
@Named
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Component
public class SearchBean {

    @NotNull
    private String buildName;
    private String buildNumber;
    private String buildId;

    private final ReportsService reportsService;
    private final I18nService i18nService;

    @Autowired
    public SearchBean(ReportsService reportsService, I18nService i18nService) {
        this.reportsService = reportsService;
        this.i18nService = i18nService;
    }

    /**
     * Perform build run search for submitted form.
     * There are multiple possible outcomes:
     *
     * 1, user submitted only projectName so list of its runs is presented
     * 2, user submitted projectName and sequentialNumber - perform redirect to build run detail
     * When no build run is found, error message is presented.
     *
     */
    public String search() {
        if (StringUtils.isEmpty(buildNumber)) {
            return BuildListBean.BUILD_RUN_LIST_OUTCOME;
        }

        BuildRunMetadataWithId buildPresentation = reportsService.getBuildRunPresentation(buildName, Long.parseLong(buildNumber));

        if (buildPresentation == null) {
            String detailMsg = MessageFormat.format(i18nService.localizeSearchBuild("no.result.detail"), buildName, buildNumber);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                                                i18nService.localizeSearchBuild("no.result"),
                                                                                detailMsg));
            return "";
        }

        buildId = buildPresentation.getId();

        return BuildDetailBean.BUILD_DETAIL_OUTCOME;
    }

    /**
     * The name of searched build.
     */
    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    /**
     * Searched build number.
     */
    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    /**
     * Id of found build run.
     */
    public String getBuildId() {
        return buildId;
    }
}
