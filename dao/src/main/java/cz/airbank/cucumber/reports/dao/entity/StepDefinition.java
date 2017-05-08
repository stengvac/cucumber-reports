package cz.airbank.cucumber.reports.dao.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition of one step.
 *
 * @author David Passler
 */
public class StepDefinition extends Statement {

    private static final long serialVersionUID = 1126601589858299741L;

    private RowTableDefinition stepDataTable;
    private List<Argument> arguments;

    /**
     * Data table associated with this step definition.
     * Usually used to initialize test DB or other needed data structures.
     *
     * @return {@code null} when table is not present
     */
    public RowTableDefinition getStepDataTable() {
        return stepDataTable;
    }

    public void setStepDataTable(RowTableDefinition stepDataTable) {
        this.stepDataTable = stepDataTable;
    }

    /**
     * Arguments used in step definition.
     *
     * @return empty list if there are no arguments.
     */
    public List<Argument> getArguments() {
        if (arguments == null) {
            arguments = new ArrayList<>();
        }

        return arguments;
    }

    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "StepDefinition{" +
                "stepDataTable=" + stepDataTable +
                ", arguments=" + arguments +
                ", name='" + getName() + '\'' +
                ", commentList=" + getCommentList() +
                ", range=" + getRange() +
                '}';
    }
}
