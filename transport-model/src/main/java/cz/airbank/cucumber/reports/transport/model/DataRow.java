package cz.airbank.cucumber.reports.transport.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent one data row in example or background section.
 *
 * @author Vaclav Stengl
 */
public class DataRow implements Serializable {

    private static final long serialVersionUID = -6400845040203446556L;

    private List<LineStatement> commentList;
    private List<String> valueList;
    private String descriptionColumn;
    private int line;

    /**
     * Comments associated with row.
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
     * Row values.
     */
    public List<String> getValueList() {
        if (valueList == null) {
            valueList = new ArrayList<>();
        }

        return valueList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }

    /**
     * Row location.
     */
    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    /**
     * This attribute is not null if examples as first column header is equal to 'DESCRIPTION'
     *
     * Description field is used as name/description for scenario outline .
     */
    public String getDescriptionColumn() {
        return descriptionColumn;
    }

    public void setDescriptionColumn(String descriptionColumn) {
        this.descriptionColumn = descriptionColumn;
    }
}
