package cz.airbank.cucumber.reports.dao.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing one feature run (one .feature file).
 *
 * @author David Passler
 */
public class FeatureRun extends TaggedStatement {

    private static final long serialVersionUID = -7297362448895119103L;

    private Integer id;
    private ScenarioDefinition background;
    private List<ScenarioDefinition> scenarioDefinitions;
    private DaoFeatureMetadata metadata;

    /**
     * Scenarios of the feature.
     */
    public List<ScenarioDefinition> getScenarioDefinitions() {
        if (scenarioDefinitions == null) {
            scenarioDefinitions = new ArrayList<>();
        }

        return scenarioDefinitions;
    }

    public void setScenarioDefinitions(List<ScenarioDefinition> scenarioDefinitions) {
        this.scenarioDefinitions = scenarioDefinitions;
    }

    /**
     * Feature metadata.
     */
    public DaoFeatureMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(DaoFeatureMetadata metadata) {
        this.metadata = metadata;
    }

    /**
     * The index of feature in {@link TestSuiteExecution#getFeatureRuns()}.
     * This index will serve as "id" of feature run in {@link TestSuiteExecution} context.
     *
     * @return the unique id within {@link TestSuiteExecution}
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Definition of background section inside .feature file.
     *
     * StepRuns associated with step definition inside background are placed in {@link ScenarioExecution#backgroundStepRuns}
     * located inside {@link #getScenarioDefinitions()} so before hook runs, background step runs, scenario step runs and after hook runs
     * are all placed in same {@link ScenarioExecution}.
     *
     * @return {@code null} when background section is not present in .feature file
     */
    public ScenarioDefinition getBackground() {
        return background;
    }

    public void setBackground(ScenarioDefinition background) {
        this.background = background;
    }

    @Override
    public String toString() {
        return "FeatureRun{" +
                "id=" + id +
                ", background=" + background +
                ", scenarioDefinitions=" + scenarioDefinitions +
                ", metadata=" + metadata +
                '}';
    }
}
