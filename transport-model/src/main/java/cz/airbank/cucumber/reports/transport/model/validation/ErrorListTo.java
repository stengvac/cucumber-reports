package cz.airbank.cucumber.reports.transport.model.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Transport object for list of validation errors.
 *
 * @author Vaclav Stengl
 */
public class ErrorListTo implements Serializable {

    private static final long serialVersionUID = 3810423444589635483L;

    @JsonProperty("errors")
    private List<ErrorTo> errorTos;

    @JsonProperty("errors")
    public List<ErrorTo> getErrorTos() {
        if (errorTos == null) {
            errorTos = new ArrayList<>();
        }

        return errorTos;
    }

    @JsonProperty("errors")
    public void setErrorTos(List<ErrorTo> errorTos) {
        this.errorTos = errorTos;
    }
}
