package cz.airbank.cucumber.reports.dao.mongo;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mongodb.DBObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import cz.airbank.cucumber.reports.dao.entity.BuildRun;

/**
 * Tests for {@link BuildRunDaoMongoDBImpl}.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class BuildRunDaoMongoDBImplTest {

    private static final String PROJECT_NAME = "projectName";
    private static final long SEQUENTIAL_NUMBER = 4;
    private static final String INCLUDED_ID = "id";
    private static final String INCLUDED_METADATA = "metadata";
    private static final String PROJECT_NAME_PATH = INCLUDED_METADATA + ".projectName";
    private static final String SEQUENTIAL_NUMBER_PATH = INCLUDED_METADATA + ".sequentialNumber";

    @Captor
    private ArgumentCaptor<Query> queryCaptor;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private BuildRunDaoMongoDBImpl buildRunDao;

    @Before
    public void setUp() {
        when(mongoTemplate.findOne(queryCaptor.capture(), eq(BuildRun.class))).thenReturn(new BuildRun());
    }

    /**
     * Query created correctly and mongo template was called.
     */
    @Test
    public void testFindByBuildNameAndSequentialNumber() {
        BuildRun run = buildRunDao.findByBuildNameAndSequentialNumber(PROJECT_NAME, SEQUENTIAL_NUMBER);
        DBObject queryObject = getQueryObject();

        assertNotNull(run);
        assertEquals(PROJECT_NAME, queryObject.get(PROJECT_NAME_PATH));
        assertEquals(SEQUENTIAL_NUMBER, queryObject.get(SEQUENTIAL_NUMBER_PATH));

        verify(mongoTemplate).findOne(queryCaptor.getValue(), BuildRun.class);
    }

    /**
     * Query created correctly and mongo template was called.
     */
    @Test
    public void testFindBuildPresentationByBuildNameAndSequentialNumber() {
        BuildRun run = buildRunDao.findBuildPresentationByBuildNameAndSequentialNumber(PROJECT_NAME, SEQUENTIAL_NUMBER);
        DBObject queryObject = getQueryObject();
        DBObject includedFields = queryCaptor.getValue().fields().getFieldsObject();

        assertNotNull(run);
        assertEquals(PROJECT_NAME, queryObject.get(PROJECT_NAME_PATH));
        assertEquals(SEQUENTIAL_NUMBER, queryObject.get(SEQUENTIAL_NUMBER_PATH));
        assertEquals(1, includedFields.get(INCLUDED_ID));
        assertEquals(1, includedFields.get(INCLUDED_METADATA));

        verify(mongoTemplate).findOne(queryCaptor.getValue(), BuildRun.class);
    }

    /**
     * Query created correctly and mongo template was called.
     */
    @Test
    public void testFindBuildPresentationByBuildName() {
        BuildRun run = buildRunDao.findBuildPresentationByBuildNameAndSequentialNumber(PROJECT_NAME, SEQUENTIAL_NUMBER);
        DBObject includedFields = queryCaptor.getValue().fields().getFieldsObject();

        assertNotNull(run);
        assertEquals(PROJECT_NAME, getQueryObject().get(PROJECT_NAME_PATH));
        assertEquals(1, includedFields.get(INCLUDED_ID));
        assertEquals(1, includedFields.get(INCLUDED_METADATA));

        verify(mongoTemplate).findOne(queryCaptor.getValue(), BuildRun.class);
    }

    /**
     * get query object from captor
     */
    private DBObject getQueryObject() {
        return queryCaptor.getValue().getQueryObject();
    }
}