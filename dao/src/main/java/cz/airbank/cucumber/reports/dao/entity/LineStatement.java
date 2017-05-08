package cz.airbank.cucumber.reports.dao.entity;

import java.io.Serializable;

/**
 * Dao representation of line statement.
 *
 * @author Vaclav Stengl
 */
public class LineStatement implements Serializable {

    private static final long serialVersionUID = -4139773651208302577L;

    private int line;
    private String statement;

    /**
     * The line in feature file, which hold statement.
     */
    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    /**
     * Statement value.
     */
    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "LineStatement{" +
                "line=" + line +
                ", statement='" + statement + '\'' +
                '}';
    }
}
