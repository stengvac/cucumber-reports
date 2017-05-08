package cz.airbank.cucumber.reports.view.reports.model.testsuite;

import java.io.Serializable;

/**
 * Envelope object for test suite metadata and test suite id.
 *
 * @author Vaclav Stengl
 */
public class TestSuiteMetadataWithId implements Serializable {

    private static final long serialVersionUID = 3258115866115774809L;

    private TestSuiteMetadata metadata;
    private String id;

    /**
     * Metadata of test suite.
     */
    public TestSuiteMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(TestSuiteMetadata metadata) {
        this.metadata = metadata;
    }

    /**
     * The id of test suite.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
