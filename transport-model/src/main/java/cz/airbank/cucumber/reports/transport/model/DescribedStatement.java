package cz.airbank.cucumber.reports.transport.model;

/**
 * Enhances {@link Statement} with description.
 *
 * @author Vaclav Stengl
 */
public class DescribedStatement extends Statement {

    private static final long serialVersionUID = 4089614134183931715L;

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
