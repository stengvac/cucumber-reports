package cz.airbank.cucumber.reports.dao.entity.config;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity class for storing config needed for app run.
 *
 * @author Vaclav Stengl
 */
@Document(collection = "config")
public class ApplicationConfig implements Serializable {

    private static final long serialVersionUID = 7992207595211777173L;

    @Id
    private String id;
    private String consoleLogsUrl;

    /**
     * The id in DB.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Contains url/path pattern where can be found logs related to executed build run.
     *
     * @return can be {@code null}
     */
    public String getConsoleLogsUrl() {
        return consoleLogsUrl;
    }

    public void setConsoleLogsUrl(String consoleLogsUrl) {
        this.consoleLogsUrl = consoleLogsUrl;
    }
}
