package cz.airbank.cucumber.reports.view.reports.model.feature;

import java.util.ArrayList;
import java.util.List;

/**
 * Taggable statement report.
 *
 * @author Vaclav Stengl
 */
public class TaggedStatementReport extends DescribedStatementReport {

    private static final long serialVersionUID = -8164209891536974343L;

    private List<LineStatementReport> tagList;

    /**
     * Tags.
     */
    public List<LineStatementReport> getTagList() {
        if (tagList == null) {
            tagList = new ArrayList<>();
        }

        return tagList;
    }

    public void setTagList(List<LineStatementReport> tagList) {
        this.tagList = tagList;
    }
}
