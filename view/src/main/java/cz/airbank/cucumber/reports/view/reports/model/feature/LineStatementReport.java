package cz.airbank.cucumber.reports.view.reports.model.feature;

import java.io.Serializable;

/**
 * Line statement.
 *
 * @author Vaclav Stengl
 */
public class LineStatementReport implements Serializable {

    private static final long serialVersionUID = -3726969402250419980L;

    private String statement;

    /**
     * Line statement value.
     */
    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
