package cz.airbank.cucumber.reports.view.reports.bean;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.stereotype.Component;

import cz.airbank.cucumber.reports.view.reports.model.feature.Argument;
import cz.airbank.cucumber.reports.view.reports.model.feature.ResultReport;
import cz.airbank.cucumber.reports.view.reports.model.feature.StepDefinitionReport;

/**
 * Bean which resolve placeholders/reserved strings inside text and bring them to live as links.
 *
 * @author Vaclav Stengl
 */
@Named
@Component
public class TextEnhanceBean {

    /**
     * Data placeholder are substituted by this link.
     */
    private static final String DATA_PLACEHOLDER_LINK = "<a class=\"data-link placeholder\" "
            + "onclick=\"replaceData(this, '%s', '%s')\">%s</a>";

    /**
     * Method which parse step names and create links for data placeholders.
     *
     * @param stepResultReport the source of step definition to process and arguments to set instead of placeholders
     * @return processed step name - placeholders are replaced by data
     */
    public String resolveStepName(ResultReport<StepDefinitionReport> stepResultReport) {
        StepDefinitionReport stepDefinition = stepResultReport.getDefinitionReport();
        String rawStepDefinition = stepDefinition.getStepDefinition();

        if (stepDefinition.getArguments().isEmpty()) {
            //if scenario is not outline there are no placeholders
            return rawStepDefinition;
        }

        List<Argument> realArguments = stepResultReport.getArguments();
        List<Argument> placeholderArguments = collectPlaceholderArguments(stepResultReport);

        StringBuilder builder = new StringBuilder();
        int indexFrom = 0;

        for (int argumentIndex = 0; argumentIndex < realArguments.size(); argumentIndex++) {
            Argument processedArgument = placeholderArguments.get(argumentIndex);
            int offSet = processedArgument.getOffset();

            String textBeforeArgument = rawStepDefinition.substring(indexFrom, offSet);
            builder.append(textBeforeArgument);

            String placeholder = processedArgument.getArgumentValue();
            String data = realArguments.get(argumentIndex).getArgumentValue();
            String argumentLink = String.format(DATA_PLACEHOLDER_LINK, placeholder, data, placeholder);
            builder.append(argumentLink);

            indexFrom = offSet + processedArgument.getArgumentValue().length() + 2 * processedArgument.getDiff();
        }

        if (indexFrom < rawStepDefinition.length()) {
            builder.append(rawStepDefinition.substring(indexFrom));
        }

        return builder.toString();
    }

    /**
     * Collect arguments which should be used as placeholders.
     *
     * @param stepResultReport the data source
     * @return if all arguments from step execution are in definition return definition args
     * otherwise add missing arguments from execution to definition as new placeholders
     */
    private List<Argument> collectPlaceholderArguments(ResultReport<StepDefinitionReport> stepResultReport) {
        List<Argument> stepDefinitionArguments = stepResultReport.getDefinitionReport().getArguments();
        List<Argument> stepExecutionArguments = stepResultReport.getArguments();

        if (stepDefinitionArguments.size() == stepExecutionArguments.size()) {
            return stepDefinitionArguments;
        }
        //step execution contains additional arguments
        return stepExecutionArguments.stream().map(executionArgument -> {
            //if arg is present in definition args use him as placeholder
            for (Argument definitionArg : stepDefinitionArguments) {
                if (executionArgument.getOffset() == definitionArg.getOffset()) {
                    return definitionArg;
                }
            }
            //is not in definitions -> return execution arg
            executionArgument.setDiff(0);
            return executionArgument;
        }).collect(Collectors.toList());
    }
}
