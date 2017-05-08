package cz.airbank.cucumber.reports.view.admin.converter;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import cz.airbank.cucumber.reports.common.converter.TwoWayConverter;
import cz.airbank.cucumber.reports.dao.entity.config.ApplicationConfig;
import cz.airbank.cucumber.reports.view.admin.model.AppConfig;

/**
 * Two way converter between DAO and view representation of application config object.
 *
 * @author Vaclav Stengl
 */
@Mapper
public interface ApplicationConfig2AppConfigConverter extends TwoWayConverter<ApplicationConfig, AppConfig> {

    @Mappings({
            @Mapping(source = "consoleLogsUrl", target = "consoleUrlPattern")
    })
    @Override
    AppConfig convert(ApplicationConfig source);

    @Mapping(ignore = true, target = "id")
    @InheritInverseConfiguration
    @Override
    ApplicationConfig convertBackward(AppConfig appConfig);
}
