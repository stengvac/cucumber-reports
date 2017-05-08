package cz.airbank.cucumber.reports.view.reports.converter;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import cz.airbank.cucumber.reports.common.converter.AbstractConverter;
import cz.airbank.cucumber.reports.view.reports.model.feature.ResultReport;


/**
 * Convert step result to css class equivalent.
 *
 * @author Vaclav Stengl
 */
@Scope
@Named
public class StepResultToCssClassConverter extends AbstractConverter<ResultReport, String> {

    @Override
    protected String convertIntern(ResultReport stepResult) {
        switch (stepResult.getStatus()) {
            case PASSED:
                return "success";
            case SKIPPED:
                return "default";
            default:
                return "danger";
        }
    }
}
