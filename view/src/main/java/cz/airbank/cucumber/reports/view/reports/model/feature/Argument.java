package cz.airbank.cucumber.reports.view.reports.model.feature;

import java.io.Serializable;

/**
 * View representation of method argument.
 *
 * @author Vaclav Stengl
 */
public class Argument implements Serializable {

    private static final long serialVersionUID = -3449329064631131642L;

    private String argumentValue;
    private int offset;
    private int diff = 1;

    /**
     * Value of argument sent to method declaration.
     */
    public String getArgumentValue() {
        return argumentValue;
    }

    public void setArgumentValue(String argumentValue) {
        this.argumentValue = argumentValue;
    }

    /**
     * The position of argument in method declaration.
     */
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }
}
