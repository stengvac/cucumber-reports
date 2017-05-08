package cz.airbank.cucumber.reports.view.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * Spring application context configuration.
 *
 * @author David Passler
 */
@Configuration
@ComponentScan(basePackages = {
        "cz.airbank.cucumber.reports.view.exceptionhandling.bean",
        "cz.airbank.cucumber.reports.view.service",
        "cz.airbank.cucumber.reports.view.statistics.bean",
        "cz.airbank.cucumber.reports.view.statistics.converter",
        "cz.airbank.cucumber.reports.view.statistics.service",
        "cz.airbank.cucumber.reports.view.reports.bean",
        "cz.airbank.cucumber.reports.view.reports.service",
        "cz.airbank.cucumber.reports.view.reports.converter",
        "cz.airbank.cucumber.reports.view.admin.bean",
        "cz.airbank.cucumber.reports.view.admin.service",
        "cz.airbank.cucumber.reports.view.admin.converter",
        "cz.airbank.cucumber.reports.view.jsfconverter"
})
@Import({
        cz.airbank.cucumber.reports.dao.config.SpringContextConfiguration.class,
        SecurityConfig.class
})
public class SpringContextConfiguration {

}
