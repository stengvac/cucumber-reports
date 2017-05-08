package cz.airbank.cucumber.reports.view.reports.service.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadata;
import cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadataWithId;

/**
 * Features grouped by module ({@link cz.airbank.cucumber.reports.view.reports.model.feature.FeatureMetadata#getModule()}).
 *
 * @author Vaclav Stengl
 */
public class FeaturesInModule implements Serializable {

    private static final long serialVersionUID = 6758912261721614460L;

    private String module;
    private List<FeatureMetadataWithId> featuresWithId;

    /**
     * Location of all {@link #getFeaturesWithId()} - eq java package.
     */
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    /**
     * Basic info about features in module.
     */
    public List<FeatureMetadataWithId> getFeaturesWithId() {
        if (featuresWithId == null) {
            featuresWithId = new ArrayList<>();
        }

        return featuresWithId;
    }

    public void setFeaturesWithId(List<FeatureMetadataWithId> featuresWithId) {
        this.featuresWithId = featuresWithId;
    }

    /**
     * Total count of executed feature files.
     */
    public int computeFeatureExecutionsTotal() {
        return getFeaturesWithId().size();
    }

    /**
     * The count of passed feature executions
     */
    public int computeFeatureExecutionsPassed() {
        return getFeaturesWithId().stream().mapToInt(
            featureMetadataWithId -> featureMetadataWithId.getMetadata().passed() ? 1 : 0
        ).sum();
    }

    /**
     * Sum of passed test executions.
     */
    public int computeTestExecutionsTotal() {
        return sumOf(FeatureMetadata::getScenarioExecutionsTotal);
    }

    /**
     * Compute passed test executions over all feature executions.
     */
    public int computeTestExecutionsPassed() {
        return sumOf(FeatureMetadata::getScenarioExecutionsPassed);
    }

    /**
     * Compute sum of provided method iterated over list.
     *
     * @param function to create sum of
     * @return computed sum
     */
    private int sumOf(Function<FeatureMetadata, Integer> function) {
        return getFeaturesWithId().stream().mapToInt(
            featureMetadataWithId -> function.apply(featureMetadataWithId.getMetadata())
        ).sum();
    }

    /**
     * Ware all feature executions in this module successful?
     *
     * @return {@code true} when all features passed.
     */
    public boolean passed() {
        return computeFeatureExecutionsPassed() == computeFeatureExecutionsTotal();
    }
}
