package cz.airbank.cucumber.reports.view.reports.converter;

import javax.inject.Named;

import org.springframework.stereotype.Component;

import cz.airbank.cucumber.reports.common.converter.AbstractConverter;

/**
 * For boolean value (result of some action) return css representation
 *
 * @author Vaclav Stengl
 */
@Named
@Component
public class BooleanToCssConverter extends AbstractConverter<Boolean, String> {

    /**
     * Css class used when converted value eq true
     */
    static final String TRUE_CSS = "success";
    /**
     * Css class used when converted value eq false
     */
    static final String FALSE_CSS = "danger bold";

    @Override
    protected String convertIntern(Boolean value) {
        if (value == null || !value) {
            return FALSE_CSS;
        }

        return TRUE_CSS;
    }
}
