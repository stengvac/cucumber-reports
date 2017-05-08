package cz.airbank.cucumber.reports.view.reports.bean;

import java.text.MessageFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadataWithId;
import cz.airbank.cucumber.reports.view.reports.service.ReportsService;
import cz.airbank.cucumber.reports.view.service.JSFUtilsService;

/**
 * Backing bean for list of build runs.
 *
 * @author Vaclav Stengl
 */
@Named
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class BuildListBean {

    /**
     * Outcome used in {@code faces-config.xml} to redirect to list of build runs.
     */
    public static final String BUILD_RUN_LIST_OUTCOME =  "toBuildList";

    /**
     * Limit of retrieved numbers.
     */
    private static final int MAX_RECORDS = 25;

    private final ReportsService reportsService;
    private final JSFUtilsService jsfUtilsService;
    private List<BuildRunMetadataWithId> presentations;
    private String buildName;

    @Autowired
    public BuildListBean(ReportsService reportsService, JSFUtilsService jsfUtilsService) {
        this.reportsService = reportsService;
        this.jsfUtilsService = jsfUtilsService;
    }

    /**
     * Obtain data.
     */
    @PostConstruct
    public void init() {
        buildName = jsfUtilsService.getPageParameter("buildName");
        Assert.notNull(buildName, MessageFormat.format("Required parameter {0} was null.", buildName));

        presentations = reportsService.getBuildRunPresentations(buildName, MAX_RECORDS);
    }

    /**
     * Found build runs.
     */
    public List<BuildRunMetadataWithId> getPresentations() {
        return presentations;
    }

    /**
     * The build name searched by.
     */
    public String getBuildName() {
        return buildName;
    }

}
