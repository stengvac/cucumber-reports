package cz.airbank.cucumber.reports.view.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import cz.airbank.cucumber.reports.view.reports.service.ReportsService;
import cz.airbank.cucumber.reports.view.service.JSFUtilsService;
import cz.airbank.cucumber.reports.view.service.ReportsServiceMockImpl;
import cz.airbank.cucumber.reports.view.service.StatisticsServiceMockImpl;
import cz.airbank.cucumber.reports.view.statistics.service.StatisticsService;

/**
 * Spring test configuration of the beans;
 *
 * @author David Passler
 */
@Configuration
@ComponentScan(basePackages = {"cz.airbank.cucumber.reports.view.statistics.bean", "cz.airbank.cucumber.reports.view.statistics.converter"})
public class SpringTestContextConfiguration {

    @Bean
    public JSFUtilsService jsfUtilsService() {
        return new JSFUtilsService();
    }

    @Bean
    public ReportsService reportsServiceMock() {
        return new ReportsServiceMockImpl();
    }

    @Bean
    public StatisticsService statisticsServiceMock() {
        return new StatisticsServiceMockImpl();
    }

}
