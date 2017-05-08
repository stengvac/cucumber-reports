package cz.airbank.cucumber.reports.view.service;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Localization service.
 *
 * @author Vaclav Stengl
 */
@Component
public class I18nService {

    private static final Logger sLog = LoggerFactory.getLogger(I18nService.class);

    /**
     * Locale of current request.
     */
    public Locale getLocale() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }

    /**
     * Translate given key with {@code searchBuild} property bundle.
     *
     * @param key to search by
     * @return translated property or key if not found.
     */
    public String localizeSearchBuild(String key) {
        return localize("cz.airbank.cucumber.reports.view.searchBuild", getLocale(), key);
    }

    /**
     * Localize string for given args via java resource bundle.
     *
     *
     * @param bundleName of bundle which should be used to localize string
     * @param locale to use
     * @param key to translate
     * @throws IllegalArgumentException when bundle name or key is {@code null} also
     *         when bundle was not found
     * @return translated text or key when key was not found
     */
    private String localize(String bundleName, Locale locale, String key) {
        Assert.notNull(bundleName);
        Assert.notNull(key);

        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, getLocale());

        Assert.notNull(bundle, MessageFormat.format("Bundle not found for bundle name {0} with locale {1}", bundleName, locale));

        if(!bundle.containsKey(key)) {
            sLog.debug("Resource bundle '{0}' for locale '{1}' - key '{2}' not found", bundleName, locale, key);
            return key;
        }

        return bundle.getString(key);
    }
}
