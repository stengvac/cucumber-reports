package cz.airbank.cucumber.reports.view.reports.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.primefaces.model.DefaultStreamedContent;

import cz.airbank.cucumber.reports.dao.EmbeddingDao;
import cz.airbank.cucumber.reports.dao.to.EmbeddingTo;
import cz.airbank.cucumber.reports.view.reports.converter.EmbeddingTo2DefaultStreamedContentConverter;

/**
 * Tests for {@link EmbeddingServiceImpl} class
 *
 * @author Vaclav Stengl
 */
@RunWith(MockitoJUnitRunner.class)
public class EmbeddingServiceImplTest {

    private static final String ID = "id";

    @Mock
    private EmbeddingDao dao;

    @Mock
    private EmbeddingTo2DefaultStreamedContentConverter contentConverter;

    @InjectMocks
    private EmbeddingServiceImpl service;

    /**
     * Image from dao layer retrieved successfully
     */
    @Test
    public void testRetrieveImage() {
        EmbeddingTo embedding = new EmbeddingTo(null, null);
        DefaultStreamedContent exceptedResult = new DefaultStreamedContent();
        when(dao.findById(ID)).thenReturn(embedding);

        when(contentConverter.convert(embedding)).thenReturn(exceptedResult);

        DefaultStreamedContent result = service.retrieveImage(ID);

        assertSame(exceptedResult, result);

        verify(dao).findById(ID);
    }
}