package cz.airbank.cucumber.reports.view.reports.model.feature;

import java.util.ArrayList;
import java.util.List;

/**
 * View object for feature data table.
 *
 * @author Vaclav Stengl
 */
public class DataTable extends TaggedStatementReport {

    private static final long serialVersionUID = 2767797869287558993L;

    private List<DataRow> dataRowList;
    private DataRow headers;

    /**
     * List of rows inside table. Each row has same amount of cells.
     */
    public List<DataRow> getDataRowList() {
        if (dataRowList == null) {
            dataRowList = new ArrayList<>();
        }

        return dataRowList;
    }

    public void setDataRowList(List<DataRow> dataRowList) {
        this.dataRowList = dataRowList;
    }

    /**
     * Column headers.
     * Description column is not included in headers it is placed to ({@link #headers#description}.
     * Description column is specially reserved column (its first column with header 'DESCRIPTION').
     */
    public DataRow getHeaders() {
        return headers;
    }

    public void setHeaders(DataRow headers) {
        this.headers = headers;
    }
}
