package cz.airbank.cucumber.reports.api.controller;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cz.airbank.cucumber.reports.api.SampleData;
import cz.airbank.cucumber.reports.api.converter.BuildRunMetadata2DaoBuildRunMetadataConverter;
import cz.airbank.cucumber.reports.api.converter.Feature2FeatureRunConverter;
import cz.airbank.cucumber.reports.api.converter.TestSuiteMetadata2DaoTestSuiteMetadataConverter;
import cz.airbank.cucumber.reports.api.exception.PersistException;
import cz.airbank.cucumber.reports.api.validation.FeatureValidator;
import cz.airbank.cucumber.reports.dao.BuildRunDao;
import cz.airbank.cucumber.reports.dao.DaoException;
import cz.airbank.cucumber.reports.dao.DataCollectDao;
import cz.airbank.cucumber.reports.dao.EmbeddingDao;
import cz.airbank.cucumber.reports.dao.entity.BuildRun;
import cz.airbank.cucumber.reports.transport.model.Feature;

/**
 * Tests for {@link DataCollectController} class.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class DataCollectControllerTest {

    @Captor
    private ArgumentCaptor<BuildRun> buildRunCaptor;

    @Mock
    private Feature2FeatureRunConverter feature2FeatureRunConverter;

    @Mock
    private TestSuiteMetadata2DaoTestSuiteMetadataConverter testSuiteMetadataConverter;

    @Mock
    private BuildRunMetadata2DaoBuildRunMetadataConverter buildRunMetadataConverter;

    @Mock
    private DataCollectDao collectDao;

    @Mock
    private BuildRunDao buildRunDao;

    @Mock
    private EmbeddingDao embeddingDao;

    @Mock
    private FeatureValidator featureValidator;

    @InjectMocks
    private DataCollectController controller;

    private Feature feature = SampleData.createFeature();
    private BuildRun buildRun = SampleData.createBuildRun();

    @Before
    public void setUp() {
        when(feature2FeatureRunConverter.convert(feature))
            .thenReturn(SampleData.createFeatureRun());
        when(buildRunMetadataConverter.convert(feature.getBuildRunMetadata()))
            .thenReturn(buildRun.getMetadata());
        when(testSuiteMetadataConverter.convert(feature.getTestSuiteMetadata()))
            .thenReturn(SampleData.createDaoTestSuiteMetadata());
        when(buildRunDao.findByBuildNameAndSequentialNumber(SampleData.PROJECT_NAME, SampleData.SEQUENTIAL_NUMBER))
            .thenReturn(buildRun);
    }

    /**
     * First feature in build run. Creates new build run.
     */
    @Test
    public void testCollectDataInsertNewBuild() {
        when(buildRunDao.findByBuildNameAndSequentialNumber(SampleData.PROJECT_NAME, SampleData.SEQUENTIAL_NUMBER)).thenReturn(null);
        doNothing().when(collectDao).persist(buildRunCaptor.capture());

        ResponseEntity responseEntity = controller.collectData(feature);

        BuildRun run = buildRunCaptor.getValue();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertTrue(run.getMetadata().isPassed());
        assertEquals(SampleData.PROJECT_NAME, run.getMetadata().getProjectName());
        assertEquals(1, run.getTestSuites().size());
        assertTrue(run.getTestSuites().get(0).getMetadata().isPassed());

        verify(embeddingDao, times(2)).store(any(InputStream.class), eq(SampleData.CONTENT_TYPE));
        verify(collectDao).persist(any(BuildRun.class));
    }

    /**
     * Update build run.
     */
    @Test
    public void testCollectDataSaveBuild() {
        ResponseEntity responseEntity = controller.collectData(feature);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(embeddingDao, times(2)).store(any(InputStream.class), eq(SampleData.CONTENT_TYPE));
    }

    /**
     * Build run update failed.
     */
    @Test(expected = PersistException.class)
    public void testCollectDataSaveBuildFailed() {
        doThrow(DaoException.class).when(collectDao).persist(buildRun);

        controller.collectData(feature);
    }

    /**
     * Obtained feature run failed so whole test suite execution and build run are marked as not successful.
     */
    @Test
    public void testCollectDataFailedFeature() {
        doNothing().when(collectDao).persist(buildRunCaptor.capture());

        ResponseEntity responseEntity = controller.collectData(feature);
        BuildRun run = buildRunCaptor.getValue();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertFalse(run.getMetadata().isPassed());
        assertEquals(SampleData.PROJECT_NAME, run.getMetadata().getProjectName());
        assertEquals(1, run.getTestSuites().size());
        //sample build - passed, new test suite - passed, empty feature run - passed
        assertTrue(run.getTestSuites().get(0).getMetadata().isPassed());
    }
}