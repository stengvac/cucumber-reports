package cz.airbank.cucumber.reports.view.reports.model.buildrun;

import java.io.Serializable;

/**
 * Envelope object for build run metadata and id.
 *
 * @author Vaclav Stengl
 */
public class BuildRunMetadataWithId implements Serializable {

    private static final long serialVersionUID = 5922755076144279748L;

    private BuildRunMetadata metadata;
    private String id;

    /**
     * Metadata about build.
     */
    public BuildRunMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(BuildRunMetadata metadata) {
        this.metadata = metadata;
    }

    /**
     * Id of build run.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
