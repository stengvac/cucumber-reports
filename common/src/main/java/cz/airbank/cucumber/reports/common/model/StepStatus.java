package cz.airbank.cucumber.reports.common.model;

/**
 * This enum is used inside project functionality to represent possible step results of
 * backing steps written in java.
 *
 * @author Vaclav Stengl
 */
public enum StepStatus {
    PASSED,
    FAILED,
    /**
     * After step inside scenario run have status pending, undefined, failed or missing following steps are skipped.
     */
    SKIPPED,
    /**
     * Step exist but is not implemented (inside step is thrown pending exception)
     */
    PENDING,
    /**
     * Step definition was not found
     */
    UNDEFINED,
    MISSING;
}
