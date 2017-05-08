package cz.airbank.cucumber.reports.dao.mongo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import cz.airbank.cucumber.reports.dao.AppConfigDao;
import cz.airbank.cucumber.reports.dao.entity.config.ApplicationConfig;

/**
 * Impl of {@link AppConfigDao}.
 *
 * @author Vaclav Stengl
 */
@Component
public class AppConfigDaoMongoImpl implements AppConfigDao {

    private static final Logger sLog = LoggerFactory.getLogger(AppConfigDaoMongoImpl.class);

    private final MongoTemplate template;

    @Autowired
    public AppConfigDaoMongoImpl(MongoTemplate template) {
        this.template = template;
    }

    @Override
    public ApplicationConfig retrieveApplicationConfig() {
        boolean collectionExists = template.collectionExists(ApplicationConfig.class);

        if (!collectionExists) {
            template.createCollection(ApplicationConfig.class);
        }

        List<ApplicationConfig> applicationConfigs = template.findAll(ApplicationConfig.class);

        if (CollectionUtils.isEmpty(applicationConfigs)) {
            return null;
        }

        ApplicationConfig usedConfig = applicationConfigs.get(0);

        if (applicationConfigs.size() > 1 && sLog.isDebugEnabled()) {
            sLog.debug("In database is more then 1 config. " +
                    "Someone probably manually inserted others. " +
                    "Config with id {} used.", usedConfig.getId());
        }

        return usedConfig;
    }

    @Override
    public void storeApplicationConfig(ApplicationConfig appConfig) {
        ApplicationConfig applicationConfig = retrieveApplicationConfig();
        //update retrieved record
        if (applicationConfig != null) {
            appConfig.setId(applicationConfig.getId());
        }

        template.insert(appConfig);
    }
}
