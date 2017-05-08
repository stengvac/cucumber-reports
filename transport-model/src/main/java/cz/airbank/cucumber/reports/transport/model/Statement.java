package cz.airbank.cucumber.reports.transport.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Common data for statements inside .feature files.
 *
 * @author Vaclav Stengl
 */
public class Statement implements Serializable {

    private static final long serialVersionUID = 1888929257594538016L;

    private String name;
    private List<LineStatement> commentList;
    private LineRange range;

    /**
     * Statement name.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Comments bind to statement.
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
     * Defines start and end of statement.
     */
    public LineRange getRange() {
        return range;
    }

    public void setRange(LineRange range) {
        this.range = range;
    }

    /**
     * Add comment
     */
    public void addComment(LineStatement comment) {
        getCommentList().add(comment);
    }
}
