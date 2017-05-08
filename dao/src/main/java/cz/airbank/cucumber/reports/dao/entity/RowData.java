package cz.airbank.cucumber.reports.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing one row data (one row in Examples or Background table of the .feature file).
 *
 * @author David Passler
 */
public class RowData implements Serializable {

    private static final long serialVersionUID = 5137739439294820793L;

    private String description;
    private List<String> data;

    /**
     * The description (the first column of the table).
     * This value is null, if there was not first column in data table with name "DESCRIPTION".
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The values of row inside data table.
     */
    public List<String> getData() {
        if (data == null) {
            data = new ArrayList<>();
        }

        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RowData{" +
                "description='" + description + '\'' +
                ", data=" + data +
                '}';
    }
}
