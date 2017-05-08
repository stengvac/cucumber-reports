package cz.airbank.cucumber.reports.transport.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent step inside {@code Background}, {@code Scenario} or {@code Scenario Outline}.
 *
 * @author Vaclav Stengl
 */
public class StepDefinition extends Statement {

    private static final long serialVersionUID = -7074795302832948640L;

    private String keyword;
    private DataTable stepDataTable;
    private List<Argument> arguments;

    /**
     * Step keyword is equal to used step annotation (Given, And, Then, ...)
     */
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Data table associated with this step definition.
     * Usually used to initialize test DB or other needed data structures.
     *
     * @return {@code null} when table is not present
     */
    public DataTable getStepDataTable() {
        return stepDataTable;
    }

    public void setStepDataTable(DataTable stepDataTable) {
        this.stepDataTable = stepDataTable;
    }

    /**
     * Arguments present in step definition declaration. Only arguments declared inside feature file are
     * present. Argument declared via annotation are present only in {@link StepResult#getArguments()}.
     *
     * @return empty list if there are no arguments in method declaration
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
}
