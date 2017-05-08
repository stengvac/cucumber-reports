package cz.airbank.cucumber.reports.transport.model;

import java.io.Serializable;

/**
 * Represent argument in function.
 *
 * @author Vaclav Stengl
 */
public class Argument implements Serializable {

    private static final long serialVersionUID = 8122845009125972096L;

    private String argumentValue;
    private int offset;

    /**
     * The value of argument in method declaration.
     */
    public String getArgumentValue() {
        return argumentValue;
    }

    public void setArgumentValue(String argumentValue) {
        this.argumentValue = argumentValue;
    }

    /**
     * The position of hook argument.
     */
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
