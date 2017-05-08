package cz.airbank.cucumber.reports.transport.model.validation;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Validation result TO.
 *
 * @author Vaclav Stengl
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "code",
        "attribute",
        "message"
})
public class ErrorTo implements Serializable {

    private static final long serialVersionUID = 3935375181126788535L;

    @JsonProperty("code")
    private String code;
    @JsonProperty("attribute")
    private String attribute;
    @JsonProperty("message")
    private String message;

    @JsonCreator
    public ErrorTo() {
    }

    public ErrorTo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorTo(String attribute, String code, String message) {
        this.code = code;
        this.attribute = attribute;
        this.message = message;
    }

    /**
     * Validation code.
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Attribute name where validation failed.
     */
    @JsonProperty("attribute")
    public String getAttribute() {
        return attribute;
    }

    @JsonProperty("attribute")
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * Error message - non localized.
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorTo{" +
                "code='" + code + '\'' +
                ", attribute='" + attribute + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
