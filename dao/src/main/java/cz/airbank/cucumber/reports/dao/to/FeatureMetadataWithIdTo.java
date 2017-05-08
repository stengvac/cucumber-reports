package cz.airbank.cucumber.reports.dao.to;

import java.io.Serializable;

import cz.airbank.cucumber.reports.dao.entity.DaoFeatureMetadata;

/**
 * Feature metadata with feature id.
 *
 * @author Vaclav Stengl
 */
public class FeatureMetadataWithIdTo implements Serializable {

    private static final long serialVersionUID = 3011221959935596853L;

    private String id;
    private DaoFeatureMetadata metadata;

    /**
     * Feature id.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Feature metadata.
     */
    public DaoFeatureMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(DaoFeatureMetadata metadata) {
        this.metadata = metadata;
    }
}
