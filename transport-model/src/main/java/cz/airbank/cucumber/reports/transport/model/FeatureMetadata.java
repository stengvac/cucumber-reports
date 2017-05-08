package cz.airbank.cucumber.reports.transport.model;

import java.io.Serializable;

/**
 * Basic information about executed feature file.
 * Provide information about feature name, feature location alias module and used glue which specify location of
 * steps implementation.
 *
 * @author Vaclav Stengl
 */
public class FeatureMetadata implements Serializable {

    private static final long serialVersionUID = 2246120863319175433L;

    private String module;
    private String filename;
    private String glue;

    public FeatureMetadata() {
        //jackson purposes
    }

    public FeatureMetadata(String module, String filename, String glue) {
        this.module = module;
        this.filename = filename;
        this.glue = glue;
    }

    /**
     * Module where *.feature file is located.
     */
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    /**
     * Feature file name
     */
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Glue used to locate steps implementation
     */
    public String getGlue() {
        return glue;
    }

    public void setGlue(String glue) {
        this.glue = glue;
    }
}
