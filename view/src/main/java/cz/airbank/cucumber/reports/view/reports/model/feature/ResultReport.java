package cz.airbank.cucumber.reports.view.reports.model.feature;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.airbank.cucumber.reports.common.model.StepStatus;

/**
 * Step result.
 *
 * @author Vaclav Stengl
 */
public class ResultReport<T> implements Serializable {

    private static final long serialVersionUID = 1969061997485287404L;

    private StepStatus status;
    private long duration;
    private String errorMessage;
    private List<String> embeddingList;
    private List<Argument> arguments;
    private T definitionReport;

    /**
     * Step result status (passed, failed, ...)
     */
    public StepStatus getStatus() {
        return status;
    }

    public void setStatus(StepStatus status) {
        this.status = status;
    }

    /**
     * Step run duration in millis.
     *
     * @return null if step was skipped or is undefined
     */
    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * Result error message.
     *
     * @return non null value for failed or pending status
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Embedded files created during scenario run.
     */
    public List<String> getEmbeddingList() {
        if (embeddingList == null) {
            embeddingList = new ArrayList<>();
        }

        return embeddingList;
    }

    public void setEmbeddingList(List<String> embeddingList) {
        this.embeddingList = embeddingList;
    }

    /**
     * Values of arguments used to execute hook/step.
     * Step execution can contain more arguments than step definition.
     * Step definition contain only arguments declared inside feature file but
     * step execution contains all declared arguments (it is possible to declare arguments in step annotation).
     *
     * @return empty list if there are no argument for this hook/step.
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

    /**
     * The definition associated with this result.
     */
    public T getDefinitionReport() {
        return definitionReport;
    }

    public void setDefinitionReport(T definitionReport) {
        this.definitionReport = definitionReport;
    }

    @Override
    public String toString() {
        return "ResultReport{" +
                "status=" + status +
                ", duration=" + duration +
                ", errorMessage='" + errorMessage + '\'' +
                ", embeddingList=" + embeddingList +
                ", argumentValues=" + arguments +
                ", definitionReport=" + definitionReport +
                '}';
    }
}
