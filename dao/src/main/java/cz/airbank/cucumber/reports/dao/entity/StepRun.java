package cz.airbank.cucumber.reports.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.airbank.cucumber.reports.common.model.StepStatus;

/**
 * Entity representing one {@link StepDefinition} run.
 *
 * @author David Passler
 */
public class StepRun implements Serializable {

    private static final long serialVersionUID = -1409510636848823035L;

    private Long duration;
    private StepStatus stepStatus;
    private String errorMessage;
    private List<String> embeddedIds;
    private List<Argument> arguments;

    /**
     * The step execution duration.
     */
    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    /**
     * The step result.
     */
    public StepStatus getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(StepStatus stepStatus) {
        this.stepStatus = stepStatus;
    }

    /**
     * The step error message if result is not success (then the value is null).
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Ids of embedded files associated with step run.
     */
    public List<String> getEmbeddedIds() {
        if (embeddedIds == null) {
            embeddedIds = new ArrayList<>();
        }

        return embeddedIds;
    }

    public void setEmbeddedIds(List<String> embeddedIds) {
        this.embeddedIds = embeddedIds;
    }

    /**
     * Values of arguments declared in {@link StepDefinition#getArguments()}.
     * Values are mapped to {@link StepDefinition#getArguments()} by index in collection.
     *
     * @return empty list if there are no arguments in step definition.
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
        return "StepRun{" +
               "duration=" + duration +
               ", stepStatus=" + stepStatus +
               ", errorMessage='" + errorMessage + '\'' +
               ", embeddedIds=" + embeddedIds +
               ", argumentValues=" + arguments +
               '}';
    }
}
