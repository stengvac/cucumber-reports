package cz.airbank.cucumber.reports.dao;

import cz.airbank.cucumber.reports.dao.entity.config.ApplicationConfig;

/**
 * Dao for application configuration related operations.
 *
 * @author Vaclav Stengl
 */
public interface AppConfigDao {

    /**
     * Retrieve instance with application configuration.
     *
     * @return may return {@code null} if there is no config yet.
     */
    ApplicationConfig retrieveApplicationConfig();

    /**
     * Store config - only config will be overrode and lost.
     *
     * @param appConfig to store
     */
    void storeApplicationConfig(ApplicationConfig appConfig);
}
