package cz.airbank.cucumber.reports.view.reports.service;

import org.primefaces.model.DefaultStreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.airbank.cucumber.reports.dao.EmbeddingDao;
import cz.airbank.cucumber.reports.view.reports.converter.EmbeddingTo2DefaultStreamedContentConverter;

/**
 * Implementation of {@link EmbeddingService}.
 *
 * @author Vaclav Stengl
 */
@Service
public class EmbeddingServiceImpl implements EmbeddingService {

    private final EmbeddingDao embeddingDao;
    private final EmbeddingTo2DefaultStreamedContentConverter embeddingConverter;

    @Autowired
    public EmbeddingServiceImpl(EmbeddingDao embeddingDao, EmbeddingTo2DefaultStreamedContentConverter embeddingConverter) {
        this.embeddingDao = embeddingDao;
        this.embeddingConverter = embeddingConverter;
    }

    @Override
    public DefaultStreamedContent retrieveImage(String id) {
         return embeddingConverter.convert(embeddingDao.findById(id));
    }
}
