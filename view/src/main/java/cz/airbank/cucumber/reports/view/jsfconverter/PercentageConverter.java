package cz.airbank.cucumber.reports.view.jsfconverter;

import java.text.NumberFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.airbank.cucumber.reports.view.service.I18nService;

/**
 * Format percentage for JSF pages.
 *
 * @author Vaclav Stengl
 */
@Component
@FacesConverter("percentageConverter")
public class PercentageConverter implements Converter {

    private final I18nService i18nService;

    @Autowired
    public PercentageConverter(I18nService i18nService) {
        this.i18nService = i18nService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("PercentageConverter.getAsObject not supported.");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (!(value instanceof Double)) {
            return null;
        }

        NumberFormat percentFormatter = NumberFormat.getPercentInstance(i18nService.getLocale());

        return percentFormatter.format((double) value);
    }
}
