package cz.airbank.cucumber.reports.dao.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.Collections;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Mongo DB context configuration.
 *
 * @author Vaclav Stengl
 */
@Configuration
@EnableAspectJAutoProxy
@EnableMongoRepositories(basePackages = {"cz.airbank.cucumber.reports.dao.mongo"})
public class MongoSpringContextConfiguration extends AbstractMongoConfiguration {

    static {
        //tons of logs from mongoDB - every 10s health check..
        LogManager.getLogger("org.mongodb.driver.cluster").setLevel(Level.INFO);
    }

    @Value("${spring.data.mongodb.host:localhost}")
    private String databaseHost;

    @Value("${spring.data.mongodb.port:27017}")
    private Integer databasePort;

    @Value("${spring.data.mongodb.user:#{null}}")
    private String databaseUser;

    @Value("${spring.data.mongodb.password:#{null}}")
    private String databasePassword;

    @Value("${spring.data.mongodb.database:test}")
    private String databaseName;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Bean
    @Override
    public MongoClient mongo() {
        final ServerAddress serverAddress = new ServerAddress(databaseHost, databasePort);

        if (databaseUser != null && databasePassword != null) {
            final MongoCredential credential = MongoCredential.createCredential(databaseUser, databaseName, databasePassword.toCharArray());

            return new MongoClient(serverAddress, Collections.singletonList(credential));
        }

        return new MongoClient(serverAddress);
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }
}
