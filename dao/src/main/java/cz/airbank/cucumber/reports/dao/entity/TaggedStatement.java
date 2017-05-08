package cz.airbank.cucumber.reports.dao.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Dao representation of feature statement with tags like scenario/scenario outline sections.
 *
 * @author Vaclav Stengl
 */
public class TaggedStatement extends DescribedStatement {

    private static final long serialVersionUID = 2743578260634842268L;

    private List<LineStatement> tagList;

    /**
     * Tags associated with statement.
     *
     * @return empty list if there are no associated tags
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

    @Override
    public String toString() {
        return "TaggedStatement{" +
                "tagList=" + tagList +
                ", description='" + getDescription() + '\'' +
                ", name='" + getName() + '\'' +
                ", commentList=" + getCommentList() +
                ", range=" + getRange() +
                '}';
    }
}
