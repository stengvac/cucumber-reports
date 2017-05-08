package cz.airbank.cucumber.reports.view.reports.model.feature;

/**
 * @author Vaclav Stengl
 */
public class DescribedStatementReport extends StatementReport {

    private static final long serialVersionUID = -535709173080089098L;

    private String description;

    /**
     * Statement description.
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
