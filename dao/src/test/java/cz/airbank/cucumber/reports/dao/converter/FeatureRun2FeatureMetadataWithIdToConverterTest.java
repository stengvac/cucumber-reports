package cz.airbank.cucumber.reports.dao.converter;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.airbank.cucumber.reports.dao.entity.DaoFeatureMetadata;
import cz.airbank.cucumber.reports.dao.entity.FeatureRun;
import cz.airbank.cucumber.reports.dao.to.FeatureMetadataWithIdTo;

/**
 * Test for {@link FeatureRun2FeatureMetadataWithIdToConverter}
 *
 * @author Vaclav Stengl
 */
public class FeatureRun2FeatureMetadataWithIdToConverterTest {

    private FeatureRun2FeatureMetadataWithIdToConverter converter = new FeatureRun2FeatureMetadataWithIdToConverterImpl();

    /**
     * Successful conversion
     */
    @Test
    public void testConvert_success() {
        FeatureRun featureRun = new FeatureRun();
        DaoFeatureMetadata metadata = new DaoFeatureMetadata();
        featureRun.setMetadata(metadata);

        FeatureMetadataWithIdTo result = converter.convert(featureRun);

        assertSame(metadata, result.getMetadata());
    }

}