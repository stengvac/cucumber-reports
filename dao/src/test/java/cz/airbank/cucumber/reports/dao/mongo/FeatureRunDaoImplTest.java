package cz.airbank.cucumber.reports.dao.mongo;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.util.ReflectionTestUtils;

import cz.airbank.cucumber.reports.dao.BuildRunDao;
import cz.airbank.cucumber.reports.dao.converter.FeatureReportToConverter;
import cz.airbank.cucumber.reports.dao.entity.TestSuiteExecution;
import cz.airbank.cucumber.reports.dao.to.BuildRunMetadataWithIdTo;
import cz.airbank.cucumber.reports.dao.to.FeatureReportTo;
import cz.airbank.cucumber.reports.dao.util.SampleData;


/**
 * Tests for {@link FeatureRunDaoImpl}.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class FeatureRunDaoImplTest {

    private static final String FEATURE_RUNS_ID_KEY = "featureRuns.id";
    private static final int FEATURE_RUN_INDEX = 0;
    private static final String BUILD_RUN_ID = "id";

    @Mock
    private BuildRunDao buildRunDao;

    @Captor
    private ArgumentCaptor<Query> queryArgumentCaptor;

    @Mock
    private MongoTemplate template;

    @Mock
    private FeatureReportToConverter featureReportToConverter;

    @InjectMocks
    private FeatureRunDaoImpl featureRunDao;

    /**
     * Test whenever args are set to query, mongo template is called and result is converted.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testFindFeatureReportBySuiteAndIndex() {
        TestSuiteExecution execution = SampleData.createTestSuiteExecution();
        FeatureReportTo exceptedFeatureReport = new FeatureReportTo();
        BuildRunMetadataWithIdTo buildRunMetadataWithIdTo = new BuildRunMetadataWithIdTo();

        when(template.findOne(queryArgumentCaptor.capture(), eq(TestSuiteExecution.class))).thenReturn(execution);
        when(featureReportToConverter.convert(execution, buildRunMetadataWithIdTo)).thenReturn(exceptedFeatureReport);
        when(buildRunDao.findBuildRunMetadataWithIdById(BUILD_RUN_ID)).thenReturn(buildRunMetadataWithIdTo);

        FeatureReportTo featureReport = featureRunDao.findFeatureReportByIds(BUILD_RUN_ID,
                                                                             SampleData.TEST_SUITE_ID, FEATURE_RUN_INDEX);

        assertSame(exceptedFeatureReport, featureReport);

        Query capturedQuery = queryArgumentCaptor.getValue();
        Map<String, CriteriaDefinition> criteria = (Map<String, CriteriaDefinition>) ReflectionTestUtils.getField(capturedQuery, "criteria");

        assertEquals(SampleData.TEST_SUITE_ID, criteria.get(SampleData.ID_KEY).getCriteriaObject().get(SampleData.ID_KEY));
        assertEquals(FEATURE_RUN_INDEX, criteria.get(FEATURE_RUNS_ID_KEY).getCriteriaObject().get(FEATURE_RUNS_ID_KEY));
    }
}