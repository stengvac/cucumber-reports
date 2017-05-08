package cz.airbank.cucumber.reports.view.reports.model.feature;

import java.io.Serializable;

/**
 * View object for feature presentation.
 *
 * @author Vaclav Stengl
 */
public class FeatureMetadataWithId implements Serializable {

    private static final long serialVersionUID = 7374597097890975238L;

    private FeatureMetadata metadata;
    private String id;

    /**
     * Metadata about feature.
     */
    public FeatureMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(FeatureMetadata metadata) {
        this.metadata = metadata;
    }

    /**
     * The id of feature.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
