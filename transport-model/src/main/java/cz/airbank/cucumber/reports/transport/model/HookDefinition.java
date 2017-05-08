package cz.airbank.cucumber.reports.transport.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing java hook (method annotated with cucumber @Before or @After) called when feature run is executed.
 *
 * @author Vaclav Stengl
 */
public class HookDefinition implements Serializable {

    private static final long serialVersionUID = 4369459061492188284L;

    private String location;
    private List<Argument> arguments;

    /**
     * The location of hook. To get full path add {@link FeatureMetadata#getGlue()} as prefix.
     *
     * Syntax: filename dot separator and method name
     * Example: MySteps.beforeHook()
     */
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Arguments in hook function declaration.
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
