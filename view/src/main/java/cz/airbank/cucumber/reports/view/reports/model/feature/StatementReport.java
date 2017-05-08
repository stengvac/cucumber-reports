package cz.airbank.cucumber.reports.view.reports.model.feature;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.airbank.cucumber.reports.dao.entity.LineStatement;

/**
 * Statement report.
 *
 * @author Vaclav Stengl
 */
public class StatementReport implements Serializable {

    private static final long serialVersionUID = -1181604081764567417L;

    private String name;
    private List<LineStatement> commentList;

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
     * Comments.
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
}
