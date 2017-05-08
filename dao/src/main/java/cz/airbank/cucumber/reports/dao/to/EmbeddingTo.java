package cz.airbank.cucumber.reports.dao.to;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Transport object for embedded content.
 *
 * @author Vaclav Stengl
 */
public class EmbeddingTo implements Serializable {

    private static final long serialVersionUID = 7453696961616910914L;

    private final transient InputStream stream;
    private final String contentType;

    public EmbeddingTo(InputStream inputStream, String contentType) {
        this.contentType = contentType;
        this.stream = inputStream;
    }

    /**
     * Content type.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Streamed content.
     */
    public InputStream getStream() {
        return stream;
    }
}
