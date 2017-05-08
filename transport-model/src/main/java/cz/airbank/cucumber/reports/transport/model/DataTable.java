package cz.airbank.cucumber.reports.transport.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent table inside {@code Examples} or {@code Background} section inside Cucumber .feature file.
 *
 * @author Vaclav Stengl
 */
public class DataTable extends TagStatement {

    private static final long serialVersionUID = 5925228712299881358L;

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
