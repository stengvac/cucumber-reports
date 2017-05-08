package cz.airbank.cucumber.reports.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring application context configuration.
 *
 * @author David Passler
 */
@EnableWebMvc
@Configuration
@Import(cz.airbank.cucumber.reports.dao.config.SpringContextConfiguration.class)
@ComponentScan(basePackages = {
        "cz.airbank.cucumber.reports.api.controller",
        "cz.airbank.cucumber.reports.api.converter",
        "cz.airbank.cucumber.reports.api.validation"
})
public class SpringContextConfiguration extends WebMvcConfigurerAdapter {

    /**
     * Added support for JAVA8 time package.
     */
    @Primary
    @Bean
    public ObjectMapper getJacksonObjectMapper() {
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(timeModule);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper;
    }

}
