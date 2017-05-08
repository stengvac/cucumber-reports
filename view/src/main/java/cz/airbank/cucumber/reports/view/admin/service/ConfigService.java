package cz.airbank.cucumber.reports.view.admin.service;

import cz.airbank.cucumber.reports.view.admin.model.AppConfig;

/**
 * Service allowing to configure application.
 *
 * @author Vaclav Stengl
 */
public interface ConfigService {

    /**
     * Retrieve instance with application configuration.
     * Instance is cached and updated when new config is stored.
     *
     * @return may return {@code null} if there is no config yet.
     */
    AppConfig retrieveApplicationConfig();

    /**
     * Store config - only config will be overrode and lost.
     * Also update cached config.
     *
     * @param appConfig to store
     */
    void storeApplicationConfig(AppConfig appConfig);
}
