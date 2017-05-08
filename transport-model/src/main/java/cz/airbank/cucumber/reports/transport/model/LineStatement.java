package cz.airbank.cucumber.reports.transport.model;

import java.io.Serializable;

/**
 * Represent comments or tags in .feature files.
 *
 * @author Vaclav Stengl
 */
public class LineStatement implements Serializable {

    private static final long serialVersionUID = -3507151058129878766L;

    private String value;
    private Integer line;

    public LineStatement() {
        //jackson purposes
    }

    public LineStatement(Integer line, String value) {
        this.value = value;
        this.line = line;
    }

    /**
     * Line where statement is located.
     */
    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    /**
     * Statement value.
     */
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
