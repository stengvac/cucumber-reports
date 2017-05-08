package cz.airbank.cucumber.reports.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Common data shared between majority of keywords inside feature file.
 *
 * @author Vaclav Stengl
 */
public class Statement implements Serializable {

    private static final long serialVersionUID = -6368788171447274749L;

    private String name;
    private List<LineStatement> commentList;
    private LineRange range;

    /**
     * The name of struct in feature file.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Comments associated to statement.
     *
     * @return empty list if there are no comments.
     */
    public List<LineStatement> getCommentList() {
        if (commentList == null) {
            commentList = new ArrayList<>();
        }

        return commentList;
    }

    public void setCommentList(List<LineStatement> commentList) {
        this.commentList = commentList;
    }

    /**
     * The range of statement inside of feature file.
     */
    public LineRange getRange() {
        return range;
    }

    public void setRange(LineRange range) {
        this.range = range;
    }

    @Override
    public String toString() {
        return "Statement{" +
                "name='" + name + '\'' +
                ", commentList=" + commentList +
                ", range=" + range +
                '}';
    }
}
