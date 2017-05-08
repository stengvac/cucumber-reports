package cz.airbank.cucumber.reports.dao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import cz.airbank.cucumber.reports.dao.mongo.MongoExceptionAspect;
import cz.airbank.cucumber.reports.dao.mongo.MongoSpringContextConfiguration;

/**
 * Spring application context configuration.
 * TODO UNO-6204
 *
 * @author David Passler
 */
@Configuration
@ComponentScan(basePackages = "cz.airbank.cucumber.reports.dao")
@PropertySource(value = "classpath:application.properties")
@Import(value = MongoSpringContextConfiguration.class)
public class SpringContextConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * To avoid initialization exception aspect creation must
     * occur outside of mongo package
     */
    @Bean
    public MongoExceptionAspect mongoExceptionAspect() {
        return new MongoExceptionAspect();
    }
}
