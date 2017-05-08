package cz.airbank.cucumber.reports.view.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.airbank.cucumber.reports.dao.AppConfigDao;
import cz.airbank.cucumber.reports.dao.entity.config.ApplicationConfig;
import cz.airbank.cucumber.reports.view.admin.converter.ApplicationConfig2AppConfigConverter;
import cz.airbank.cucumber.reports.view.admin.model.AppConfig;

/**
 * Impl of {@link ConfigService}
 *
 * @author Vaclav Stengl
 */
@Component
public class ConfigServiceImpl implements ConfigService {

    private final AppConfigDao appConfigDao;
    private final ApplicationConfig2AppConfigConverter configConverter;
    private AppConfig cachedConfig;

    @Autowired
    public ConfigServiceImpl(AppConfigDao appConfigDao, ApplicationConfig2AppConfigConverter configConverter) {
        this.appConfigDao = appConfigDao;
        this.configConverter = configConverter;
    }

    @Override
    public AppConfig retrieveApplicationConfig() {
        if (cachedConfig == null) {
            AppConfig appConfig = configConverter.convert(appConfigDao.retrieveApplicationConfig());
            //cache config
            cachedConfig = appConfig == null ? new AppConfig() : appConfig;
        }

        return cachedConfig;
    }

    @Override
    public void storeApplicationConfig(AppConfig appConfig) {
        ApplicationConfig applicationConfig = configConverter.convertBackward(appConfig);
        appConfigDao.storeApplicationConfig(applicationConfig);
        //update cache
        cachedConfig = configConverter.convert(appConfigDao.retrieveApplicationConfig());
    }
}
