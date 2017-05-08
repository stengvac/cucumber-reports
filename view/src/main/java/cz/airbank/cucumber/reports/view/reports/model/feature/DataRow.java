package cz.airbank.cucumber.reports.view.reports.model.feature;

import java.util.ArrayList;
import java.util.List;

/**
 * View object representing data row.
 *
 * @author Vaclav Stengl
 */
public class DataRow {

    private List<String> valueList;
    private String descriptionColumn;

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
