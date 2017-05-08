package cz.airbank.cucumber.reports.view.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.airbank.cucumber.reports.view.reports.model.buildrun.BuildRunMetadata;

/**
 * Model object for build detail which has list of counts for modules.
 *
 * @author David Passler
 */
public class BuildDetailWithCounts implements Serializable {

    private static final long serialVersionUID = 7839729351731371266L;

    private List<ModuleBuildCounts> counts;
    private BuildRunMetadata buildRunMetadata;

    /**
     * Counts of the modules affected.
     */
    public List<ModuleBuildCounts> getCounts() {
        if (counts == null) {
            counts = new ArrayList<>();
        }

        return counts;
    }

    public void setCounts(List<ModuleBuildCounts> counts) {
        this.counts = counts;
    }

    /**
     * Build run metadata
     */
    public BuildRunMetadata getBuildRunMetadata() {
        return buildRunMetadata;
    }

    public void setBuildRunMetadata(BuildRunMetadata buildRunMetadata) {
        this.buildRunMetadata = buildRunMetadata;
    }
}
