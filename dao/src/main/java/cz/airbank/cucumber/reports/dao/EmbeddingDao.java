package cz.airbank.cucumber.reports.dao;

import java.io.InputStream;

import cz.airbank.cucumber.reports.dao.to.EmbeddingTo;

/**
 * Dao for embedded files.
 *
 * @author Vaclav Stengl
 */
public interface EmbeddingDao {

    /**
     * Find embedded file for given id.
     *
     * @param id to search by
     * @return found embedding, or null when embedding does not exist
     */
    EmbeddingTo findById(String id);

    /**
     * Store embedding.
     *
     * @param stream to store
     * @param contentType of streamed content
     * @return id of stored file
     */
    String store(InputStream stream, String contentType);
}
