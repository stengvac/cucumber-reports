package cz.airbank.cucumber.reports.dao.mongo;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.mongodb.gridfs.GridFSDBFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import cz.airbank.cucumber.reports.dao.to.EmbeddingTo;
import cz.airbank.cucumber.reports.dao.util.SampleData;

/**
 * Test for {@link EmbeddingDaoImpl} class.
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class EmbeddingDaoImplTest {

    private static final String ID = "id";
    private static final String CONTENT_TYPE = "type";

    @Captor
    private ArgumentCaptor<Query> queryArgumentCaptor;

    @Mock
    private GridFsTemplate gridFsTemplate;

    @InjectMocks
    private EmbeddingDaoImpl repository;

    /**
     * Embedding for given id was retrieved correctly.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testFindById() throws Exception {
        GridFSDBFile file = new GridFSDBFile();
        file.put(SampleData.ID_KEY, ID);
        file.put("contentType", CONTENT_TYPE);

        when(gridFsTemplate.findOne(queryArgumentCaptor.capture())).thenReturn(file);

        EmbeddingTo embedding = repository.findById(ID);
        assertNotNull(embedding);
        assertEquals(CONTENT_TYPE, embedding.getContentType());
        assertNotNull(embedding.getStream());

        Map<String, CriteriaDefinition> criteria = (Map<String, CriteriaDefinition>) ReflectionTestUtils.getField(queryArgumentCaptor.getValue(), "criteria");

        assertEquals(ID, criteria.get(SampleData.ID_KEY).getCriteriaObject().get(SampleData.ID_KEY));
        verify(gridFsTemplate).findOne(queryArgumentCaptor.getValue());
    }

    /**
     * Embedding was stored properly.
     */
    @Test
    public void testStore() {
        InputStream stream = new ByteArrayInputStream(new byte[1]);
        GridFSDBFile file = new GridFSDBFile();
        file.put(SampleData.ID_KEY, ID);

        when(gridFsTemplate.store(stream, null, CONTENT_TYPE)).thenReturn(file);

        assertEquals(ID, repository.store(stream, CONTENT_TYPE));

        verify(gridFsTemplate).store(stream, null, CONTENT_TYPE);
    }

}