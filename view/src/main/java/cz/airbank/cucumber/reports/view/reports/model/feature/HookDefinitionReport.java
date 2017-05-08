package cz.airbank.cucumber.reports.view.reports.model.feature;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * View object for hook definition in report section.
 *
 * @author Vaclav Stengl
 */
public class HookDefinitionReport implements Serializable {

    private static final long serialVersionUID = -8120449757526731709L;

    private String location;
    private List<Argument> arguments;

    /**
     * The location of hook in java class.
     * To get full location of file use {@link FeatureMetadata#getGlue()} as prefix.
     */
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Arguments of hook method.
     *
     * @return empty list if there are no arguments present.
     */
    public List<Argument> getArguments() {
        if (arguments == null) {
            arguments = new ArrayList<>();
        }

        return arguments;
    }

    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }
}
