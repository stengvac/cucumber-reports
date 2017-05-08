package cz.airbank.cucumber.reports.view.reports.bean.support;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;

/**
 * Support bean for common presentation purposes.
 *
 * @author Vaclav Stengl
 */
@Named
@Scope
public class PresentationSupportBean {

    /**
     * From map create list of strings where one record is created as "<key> = <value>"
     *
     * @param valuesToPresent to representations for
     */
    public List<String> createMapPresentation(Map<String, String> valuesToPresent) {
        if (CollectionUtils.isEmpty(valuesToPresent)) {
            return null;
        }

        return valuesToPresent.entrySet().stream().map(
                (valueToPresent) -> valueToPresent.getKey() + " = " + valueToPresent.getValue()
        ).collect(Collectors.toList());
    }
}
