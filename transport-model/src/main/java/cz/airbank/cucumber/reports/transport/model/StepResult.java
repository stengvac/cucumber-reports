package cz.airbank.cucumber.reports.transport.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Step result.
 *
 * @author Vaclav Stengl
 */
public class StepResult implements Serializable {

    private static final long serialVersionUID = 1969061997485287404L;

    private StepStatus status;
    private long duration;
    private String errorMessage;
    private List<Embedding> embeddingList;
    private List<Argument> arguments;

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
    public List<Embedding> getEmbeddingList() {
        if (embeddingList == null) {
            embeddingList = new ArrayList<>();
        }

        return embeddingList;
    }

    public void setEmbeddingList(List<Embedding> embeddingList) {
        this.embeddingList = embeddingList;
    }

    /**
     * Real values of arguments used to run step. Number of arguments in step definition and
     * step execution may differ. Step definition contains only argument declared in feature file, but Cucumber
     * framework allow to declare arguments in step annotation and those are available only in step execution.
     *
     * So additional arguments are not present in {@link StepDefinition#getArguments()}
     * (mapping is possible via {@link Argument#getOffset()}.
     *
     * @return empty list if there are no arguments used in step.
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
