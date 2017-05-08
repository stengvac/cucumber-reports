package cz.airbank.cucumber.reports.dao.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.dao.to.BuildRunMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.BuildRunWithTestSuitesTo;
import cz.airbank.cucumber.reports.dao.to.TestSuiteWithFeaturesMetadataTo;
import cz.airbank.cucumber.reports.dao.util.SampleData;

/**
 * Tests for {@link BuildRun2BuildRunWithTestSuitesToMapper} impl.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class BuildRun2BuildRunWithTestSuitesToMapperTest {

    @Mock
    private BuildRun2BuildRunMetadataWithIdToConverter buildRunMetadataWithIdToConverter;

    @InjectMocks
    private BuildRun2BuildRunWithTestSuitesToMapper mapper = new BuildRun2BuildRunWithTestSuitesToMapperImpl();

    @Test
    public void testConvert_notNullInput() {
        BuildRun buildRun = SampleData.createBuildRun();
        BuildRunMetadataWithIdTo expectedMetadataWithId = new BuildRunMetadataWithIdTo();
        TestSuiteWithFeaturesMetadataTo expectedTestSuiteWith = new TestSuiteWithFeaturesMetadataTo();

        when(buildRunMetadataWithIdToConverter.convert(buildRun)).thenReturn(expectedMetadataWithId);

        BuildRunWithTestSuitesTo converted = mapper.convert(buildRun, Collections.singletonMap(SampleData.TEST_SUITE_ID,
                                                                                               expectedTestSuiteWith));

        assertEquals(1, converted.getTestSuiteWithFeaturesMetadataToes().size());
        assertSame(expectedTestSuiteWith, converted.getTestSuiteWithFeaturesMetadataToes().get(0));
        assertSame(expectedMetadataWithId, converted.getBuildRunMetadataWithIdTo());
    }

}