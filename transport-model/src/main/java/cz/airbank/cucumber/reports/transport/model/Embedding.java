package cz.airbank.cucumber.reports.transport.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Class representing embedded files.
 *
 * @author Vaclav Stengl
 */
public class Embedding implements Serializable {

    private static final long serialVersionUID = -1277576121584935220L;

    private String id;
    private byte[] content;
    private String contentType;

    public Embedding() {
        //jackson purposes
    }

    /**
     * Create new embedding instance.
     *
     * @param content of embedded file represent by byte array (copy of original array is stored) when null is provided store null
     * @param contentType of embedded file (for possible values see https://en.wikipedia.org/wiki/Media_type)
     */
    public Embedding(byte[] content, String contentType) {
        this.content = content == null ? null : Arrays.copyOf(content, content.length);
        this.contentType = contentType;
    }

    /**
     * Content of embedded file.
     */
    public byte[] getContent() {
        return Arrays.copyOf(content, content.length);
    }

    public void setContent(byte[] content) {
        this.content = content == null ? null : Arrays.copyOf(content, content.length);
    }

    /**
     * Content type.
     */
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * The id of embedded file.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
