package cz.airbank.cucumber.reports.view.reports.service;

import org.primefaces.model.DefaultStreamedContent;

/**
 * Service for embedding.
 *
 * @author Vaclav Stengl
 */
public interface EmbeddingService {

    /**
     * For given ids retrieve images and transform them to streamed content for view purposes.
     *
     * @param id to search by
     * @return list of streamed content
     */
    DefaultStreamedContent retrieveImage(String id);
}
