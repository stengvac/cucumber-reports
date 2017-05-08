package cz.airbank.cucumber.reports.transport.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Tag statement add tag list to statements.
 *
 * @author Vaclav Stengl
 */
public class TagStatement extends DescribedStatement {

    private static final long serialVersionUID = 1438932014135339592L;

    private List<LineStatement> tagList;

    /**
     * Tags associated with statement - step, feature, scenario etc.
     */
    public List<LineStatement> getTagList() {
        if (tagList == null) {
            tagList = new ArrayList<>();
        }

        return tagList;
    }

    public void setTagList(List<LineStatement> tagList) {
        this.tagList = tagList;
    }

    /**
     * Add tag to statement
     */
    public void addTag(LineStatement tag) {
        getTagList().add(tag);
    }
}
