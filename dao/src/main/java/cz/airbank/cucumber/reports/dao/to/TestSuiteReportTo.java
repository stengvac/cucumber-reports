package cz.airbank.cucumber.reports.dao.to;

import java.util.ArrayList;
import java.util.List;

/**
 * Transport object for test suite report.
 * Contains basic data about test suite and executed features.
 *
 * @author Vaclav Stengl
 */
public class TestSuiteReportTo {

    private TestSuiteMetadataWithIdTo testSuiteMetadataWithIdTo;
    private List<FeatureMetadataWithIdTo> featureMetadataWithIdTos;

    /**
     * Features executed in this test suite.
     */
    public List<FeatureMetadataWithIdTo> getFeatureMetadataWithIdTos() {
        if (featureMetadataWithIdTos == null) {
            featureMetadataWithIdTos = new ArrayList<>();
        }

        return featureMetadataWithIdTos;
    }

    public void setFeatureMetadataWithIdTos(List<FeatureMetadataWithIdTo> featureMetadataWithIdTos) {
        this.featureMetadataWithIdTos = featureMetadataWithIdTos;
    }

    /**
     * Metadata and id of reported feature.
     */
    public TestSuiteMetadataWithIdTo getTestSuiteMetadataWithIdTo() {
        return testSuiteMetadataWithIdTo;
    }

    public void setTestSuiteMetadataWithIdTo(TestSuiteMetadataWithIdTo testSuiteMetadataWithIdTo) {
        this.testSuiteMetadataWithIdTo = testSuiteMetadataWithIdTo;
    }
}
