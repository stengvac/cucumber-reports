package cz.airbank.cucumber.reports.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao entity for before and after hooks.
 *
 * @author Vaclav Stengl
 */
public class HookDefinition implements Serializable {

    private static final long serialVersionUID = 6548015147554860812L;

    private String location;
    private List<Argument> arguments;

    /**
     * Location of hook (file name and method declaration).
     */
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Arguments sent to hook.
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

    @Override
    public String toString() {
        return "HookDefinition{" +
                "location='" + location + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}
