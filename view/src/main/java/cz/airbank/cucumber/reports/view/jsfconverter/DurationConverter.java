package cz.airbank.cucumber.reports.view.jsfconverter;

import java.util.concurrent.TimeUnit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * Convert millis to human readable format.
 *
 * @author Vaclav Stengl
 */
@FacesConverter("durationConverter")
public class DurationConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (!(o instanceof Long)) {
            return null;
        }

        return DurationFormatUtils.formatDuration(TimeUnit.NANOSECONDS.toMillis((long) o), "mm:ss.S");
    }
}
