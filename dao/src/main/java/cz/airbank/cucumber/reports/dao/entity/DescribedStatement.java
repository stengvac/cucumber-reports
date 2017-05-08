package cz.airbank.cucumber.reports.dao.entity;

/**
 * Dao representation of statement inside feature file with description like background section.
 *
 * @author Vaclav Stengl
 */
public class DescribedStatement extends Statement {

    private static final long serialVersionUID = 5341596399215628657L;

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

    @Override
    public String toString() {
        return "DescribedStatement{" +
                "description='" + description + '\'' +
                ", name='" + getName() + '\'' +
                ", commentList=" + getCommentList() +
                ", range=" + getRange() +
                '}';
    }
}
