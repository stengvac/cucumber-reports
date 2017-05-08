package cz.airbank.cucumber.reports.api.validation;

/**
 * Object with information of trespassing validation rules.
 *
 * @author Vaclav Stengl
 */
public class ValidationConstraintViolation {

    private String attribute;
    private String code;

    /**
     * Attribute where constraint violation occurred.
     */
    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * Violation code.
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
