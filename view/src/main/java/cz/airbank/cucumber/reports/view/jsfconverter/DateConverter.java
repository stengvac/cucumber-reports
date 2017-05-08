package cz.airbank.cucumber.reports.view.jsfconverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.airbank.cucumber.reports.view.service.JSFUtilsService;

/**
 * Date converter used for date presentation.
 *
 * @author Vaclav Stengl
 */
@Component
@FacesConverter("dateConverter")
public class DateConverter implements Converter {

    private final JSFUtilsService jsfUtilsService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");

    @Autowired
    public DateConverter(JSFUtilsService jsfUtilsService) {
        this.jsfUtilsService = jsfUtilsService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new NotImplementedException("No need to impl for now");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (!(value instanceof LocalDateTime)) {
            return null;
        }

        return formatter.withLocale(jsfUtilsService.returnBrowserLocale()).format((LocalDateTime) value);
    }
}
