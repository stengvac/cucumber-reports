package cz.airbank.cucumber.reports.transport.model;

/**
 * Scenario types
 *
 * @author Vaclav Stengl
 */
public enum ScenarioType {
    /**
     * Specify exactly one run with given data
     */
    SCENARIO,
    /**
     * 1..n runs according to outline with given data table
     */
    SCENARIO_OUTLINE,
    /**
     * Common predecessor for scenario or scenario outline
     */
    BACKGROUND
}
