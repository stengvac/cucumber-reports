package cz.airbank.cucumber.reports.view.admin.bean;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import cz.airbank.cucumber.reports.dao.DaoException;
import cz.airbank.cucumber.reports.view.CommonOutcomes;
import cz.airbank.cucumber.reports.view.admin.model.AppConfig;
import cz.airbank.cucumber.reports.view.admin.service.ConfigService;
import cz.airbank.cucumber.reports.view.service.JSFUtilsService;

/**
 * Backing bean for admin page which allow to configure some application aspects.
 *
 * @author Vaclav Stengl
 */
@Scope(WebApplicationContext.SCOPE_SESSION)
@Named
public class AppConfigurationBean {

    private static final Logger sLog = LoggerFactory.getLogger(AppConfigurationBean.class);

    /**
     * Outcome for application config page.
     */
    public static final String APP_CONFIG_OUTCOME = "toAppConfig";

    private final ConfigService configService;
    private final JSFUtilsService jsfUtilsService;

    private AppConfig appConfig;

    @Autowired
    public AppConfigurationBean(ConfigService configService, JSFUtilsService jsfUtilsService) {
        this.configService = configService;
        this.jsfUtilsService = jsfUtilsService;
    }

    @PostConstruct
    public void init() {
        AppConfig retrievedConfig = configService.retrieveApplicationConfig();
        //so cached config is not modified
        this.appConfig = new AppConfig(retrievedConfig);
    }

    /**
     * Config instance to perform changes on.
     */
    public AppConfig getAppConfig() {
        return appConfig;
    }

    /**
     * Persist changed config object.
     *
     * @return outcome for redirect
     */
    public String submitChanges() {
        //page is only in EN
        try {
            configService.storeApplicationConfig(appConfig);
            jsfUtilsService.postInfoMessage("Saved.");
        } catch (DaoException e) {
            sLog.debug("", e);

            jsfUtilsService.postWarningMessage("Configuration was not saved.");
            return "";
        }

        return CommonOutcomes.INDEX_OUTCOME;
    }
}
