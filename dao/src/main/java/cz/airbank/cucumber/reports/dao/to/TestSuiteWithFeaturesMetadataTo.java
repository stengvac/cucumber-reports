package cz.airbank.cucumber.reports.dao.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.airbank.cucumber.reports.dao.entity.DaoFeatureMetadata;

/**
 * Transport object for presenting features inside test suite.
 *
 * @author Vaclav Stengl
 */
public class TestSuiteWithFeaturesMetadataTo implements Serializable {

    private TestSuiteMetadataWithIdTo testSuiteMetadataWithIdTo;
    private List<DaoFeatureMetadata> featureMetadataList;

    /**
     * Metadata about test suite.
     */
    public TestSuiteMetadataWithIdTo getTestSuiteMetadataWithIdTo() {
        return testSuiteMetadataWithIdTo;
    }

    public void setTestSuiteMetadataWithIdTo(TestSuiteMetadataWithIdTo testSuiteMetadataWithIdTo) {
        this.testSuiteMetadataWithIdTo = testSuiteMetadataWithIdTo;
    }

    /**
     * Metadata of feature files executed in this suite.
     */
    public List<DaoFeatureMetadata> getFeatureMetadataList() {
        if (featureMetadataList == null) {
            featureMetadataList = new ArrayList<>();
        }

        return featureMetadataList;
    }

    public void setFeatureMetadataList(List<DaoFeatureMetadata> featureMetadataList) {
        this.featureMetadataList = featureMetadataList;
    }
}
