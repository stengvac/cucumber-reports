package cz.airbank.cucumber.reports.dao.to;

import java.io.Serializable;

import cz.airbank.cucumber.reports.dao.entity.DaoTestSuiteMetadata;

/**
 * To with some basic info about test suite execution - id and metadata.
 *
 * @author Vaclav Stengl
 */
public class TestSuiteMetadataWithIdTo implements Serializable {

    private static final long serialVersionUID = 7277202428940367435L;

    private String id;
    private DaoTestSuiteMetadata metadata;

    /**
     * The id of test suite.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Test suite metadata.
     */
    public DaoTestSuiteMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(DaoTestSuiteMetadata metadata) {
        this.metadata = metadata;
    }
}
