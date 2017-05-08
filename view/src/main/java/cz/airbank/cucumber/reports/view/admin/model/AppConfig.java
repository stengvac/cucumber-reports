package cz.airbank.cucumber.reports.view.admin.model;

import java.io.Serializable;

/**
 * View object for application config.
 *
 * @author Vaclav Stengl
 */
public class AppConfig implements Serializable {

    private static final long serialVersionUID = -3840008773475986047L;

    private String consoleUrlPattern;

    public AppConfig() {
        //to create new instance
    }

    /**
     * Copy constructor.
     */
    public AppConfig(AppConfig appConfig) {
        this.consoleUrlPattern = appConfig.consoleUrlPattern;
    }

    /**
     * Pattern used to create URL/path to console with logs produced during build execution.
     *
     * @return can return {@code null}
     */
    public String getConsoleUrlPattern() {
        return consoleUrlPattern;
    }

    public void setConsoleUrlPattern(String consoleUrlPattern) {
        this.consoleUrlPattern = consoleUrlPattern;
    }
}
