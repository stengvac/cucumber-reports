package cz.airbank.cucumber.reports.dao.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition entity which represents the table with example data (Examples or table in Background).
 *
 * @author David Passler
 */
public class RowTableDefinition extends TaggedStatement {

    private static final long serialVersionUID = 1628705791228383069L;

    /**
     * The header of the table with labels for columns.
     */
    private RowData tableHeader;

    /**
     * The actual rows.
     */
    private List<RowData> rowData;

    /**
     * Gets table header.
     *
     * @return the table header
     */
    public RowData getTableHeader() {
        return tableHeader;
    }

    /**
     * Sets table header.
     *
     * @param tableHeader the table header
     */
    public void setTableHeader(RowData tableHeader) {
        this.tableHeader = tableHeader;
    }

    /**
     * Gets (lazy loaded) scenario run data entities.
     *
     * @return the scenario run data entities
     */
    public List<RowData> getRowData() {
        if (rowData == null) {
            rowData = new ArrayList<>();
        }

        return rowData;
    }

    /**
     * Sets scenario run data entities.
     *
     * @param rowData the scenario run data entities
     */
    public void setRowData(List<RowData> rowData) {
        this.rowData = rowData;
    }

    @Override
    public String toString() {
        return "RowTableDefinition{" +
                "description='" + getDescription() + '\'' +
                ", tableHeader=" + tableHeader +
                ", name='" + getName() + '\'' +
                ", rowData=" + rowData +
                ", tagList=" + getTagList() +
                ", commentList=" + getCommentList() +
                ", range=" + getRange() +
                '}';
    }
}
