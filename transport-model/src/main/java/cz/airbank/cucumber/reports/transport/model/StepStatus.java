package cz.airbank.cucumber.reports.transport.model;

/**
 * Possible step statutes of cucumber step.
 *
 * @author Vaclav Stengl
 */
public enum StepStatus {
    PASSED("passed"),
    FAILED("failed"),
    /**
     * After step inside scenario run have status pending, undefined, failed or missing following steps are skipped.
     */
    SKIPPED("skipped"),
    /**
     * Step exist but is not implemented (inside step is thrown pending exception)
     */
    PENDING("pending"),
    /**
     * Step definition was not found
     */
    UNDEFINED("undefined"),
    MISSING("missing");

    private final String value;

    StepStatus(String value) {
        this.value = value;
    }

    /**
     * Cucumber status representation.
     */
    public String getValue() {
        return value;
    }

    /**
     * For given string return status.
     *
     * @param status to process
     * @throws IllegalStateException if status was not found
     */
    public static StepStatus resolveStatus(String status) {
        for (StepStatus stepStatus : values()) {
            if (stepStatus.getValue().equals(status)) {
                return stepStatus;
            }
        }

        throw new IllegalStateException("Undefined step status:" + status);
    }
}
