package cz.airbank.cucumber.reports.view.jsfconverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

/**
 * JSF converter for date time object.
 *
 * @author Vaclav Stengl
 */
@Scope
@Named
@FacesConverter("dateTimeConverter")
public class DateTimeConverter implements Converter {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd. MM. yyyy hh:mm");

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("no need to convert string to java.time.LocalDateTime for now.");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (!(value instanceof LocalDateTime)) {
            return null;
        }

        LocalDateTime localDateTime = (LocalDateTime) value;

        return dateTimeFormatter.format(localDateTime);
    }
}
