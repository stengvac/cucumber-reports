package cz.airbank.cucumber.reports.view.reports.model.feature;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent step inside {@code Background}, {@code Scenario} or {@code Scenario Outline}.
 *
 * @author Vaclav Stengl
 */
public class StepDefinitionReport implements Serializable {

    private static final long serialVersionUID = -7074795302832948640L;

    private String stepDefinition;
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
     * The real content of step definition. Its eq to whole text in feature without keyword.
     */
    public String getStepDefinition() {
        return stepDefinition;
    }

    public void setStepDefinition(String stepDefinition) {
        this.stepDefinition = stepDefinition;
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
     * Names of arguments which are used to execute declared step.
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
        return "StepDefinitionReport{" +
               "stepDefinition='" + stepDefinition + '\'' +
               ", keyword='" + keyword + '\'' +
               ", stepDataTable=" + stepDataTable +
               ", argumentList=" + arguments +
               '}';
    }
}
