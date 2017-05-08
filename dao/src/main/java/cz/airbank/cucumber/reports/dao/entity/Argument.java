package cz.airbank.cucumber.reports.dao.entity;

import java.io.Serializable;

/**
 * Dao entity for method argument.
 *
 * @author Vaclav Stengl
 */
public class Argument implements Serializable {

    private static final long serialVersionUID = -159683013824476202L;

    private String argumentValue;
    private int offset;

    /**
     * The value of argument.
     */
    public String getArgumentValue() {
        return argumentValue;
    }

    public void setArgumentValue(String argumentValue) {
        this.argumentValue = argumentValue;
    }

    /**
     * The position of hook argument in method declaration.
     */
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "Argument{" +
                "argumentValue='" + argumentValue + '\'' +
                ", offset=" + offset +
                '}';
    }
}
