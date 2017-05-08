package cz.airbank.cucumber.reports.dao.to;

import java.io.Serializable;

import cz.airbank.cucumber.reports.dao.entity.DaoBuildRunMetadata;

/**
 * Build run metadata with id TO.
 *
 * @author Vaclav Stengl
 */
public class BuildRunMetadataWithIdTo implements Serializable {

    private static final long serialVersionUID = 61126251820479669L;

    private String id;
    private DaoBuildRunMetadata metadata;

    /**
     * Id of build run.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Build run metadata.
     */
    public DaoBuildRunMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(DaoBuildRunMetadata metadata) {
        this.metadata = metadata;
    }
}
