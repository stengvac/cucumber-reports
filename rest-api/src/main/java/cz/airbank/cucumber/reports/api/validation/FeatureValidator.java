package cz.airbank.cucumber.reports.api.validation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import cz.airbank.cucumber.reports.api.converter.ValidationResult2ErrorToConverter;
import cz.airbank.cucumber.reports.api.exception.ValidationConstraintViolationException;
import cz.airbank.cucumber.reports.transport.model.Feature;
import cz.airbank.cucumber.reports.transport.model.ScenarioDefinition;
import cz.airbank.cucumber.reports.transport.model.ScenarioRun;
import cz.airbank.cucumber.reports.transport.model.StepResult;
import cz.airbank.cucumber.reports.transport.model.validation.ErrorTo;

/**
 * Class which validate incoming feature instance.
 *
 * @author Vaclav Stengl
 */
//TODO create lexicon?
@Component
public class FeatureValidator {

    private final ValidationResult2ErrorToConverter converter;

    @Autowired
    public FeatureValidator(ValidationResult2ErrorToConverter converter) {
        this.converter = converter;
    }

    /**
     * Validate feature object obtained from rest API.
     *
     * @param feature to validated
     * @throws ValidationConstraintViolationException if there are validation errors
     */
    public void validateFeature(Feature feature) {
        List<ValidationConstraintViolation> validationConstraintViolations = new ArrayList<>();

        final int backgroundStepDefinitions;
        if (feature.getBackground() != null) {
            backgroundStepDefinitions = feature.getBackground().getStepDefinitionList().size();
        } else {
            backgroundStepDefinitions = 0;
        }

        List<ScenarioDefinition> scenarioDefinitionList = feature.getScenarioDefinitionList();
        for (int defIndex = 0; defIndex < scenarioDefinitionList.size(); defIndex++) {
            ScenarioDefinition scenarioDefinition = scenarioDefinitionList.get(defIndex);
            int scenarioStepDefinitions = scenarioDefinition.getStepDefinitionList().size();
            //there should be at least 1 scenario run
            if (CollectionUtils.isEmpty(scenarioDefinition.getScenarioRunList())) {
                String attribute = MessageFormat.format(
                        "scenarioDefinitions[{0}].scenarioRuns",
                        String.valueOf(defIndex)
                );
                ValidationConstraintViolation validationConstraintViolation = createValidationResult(attribute, "empty");
                validationConstraintViolations.add(validationConstraintViolation);
            }
            //empty step definitions are not allowed
            if (scenarioStepDefinitions == 0) {
                String attribute = MessageFormat.format(
                        "scenarioDefinitions[{0}].stepDefinitions",
                        String.valueOf(defIndex)
                );
                ValidationConstraintViolation validationConstraintViolation = createValidationResult(attribute, "empty");
                validationConstraintViolations.add(validationConstraintViolation);
            }

            for (int scenarioRunInd = 0; scenarioRunInd < scenarioDefinition.getScenarioRunList().size(); scenarioRunInd++) {
                ScenarioRun scenarioRun = scenarioDefinition.getScenarioRunList().get(scenarioRunInd);
                //create pattern for following validations
                String attributePattern = MessageFormat.format("scenarioDefinitions[{0}].scenarioRuns[{1}].{2}",
                        String.valueOf(defIndex), String.valueOf(scenarioRunInd), "{0}");

                //before hook results and definitions have same len
                validateDefinitionAndResultsLen(
                        scenarioRun.getBeforeHookResults(), scenarioDefinition.getBeforeHooks().size(),
                        attributePattern, "beforeHooks", validationConstraintViolations
                );
                //background results and definitions have same len
                validateDefinitionAndResultsLen(
                        scenarioRun.getBackgroundStepResults(), backgroundStepDefinitions,
                        attributePattern, "backgroundStepResults", validationConstraintViolations
                );
                //scenario steps results and definitions have same len
                validateDefinitionAndResultsLen(
                        scenarioRun.getScenarioStepResults(), scenarioStepDefinitions,
                        attributePattern, "scenarioStepResults", validationConstraintViolations
                );
                //after hook results and definitions have same len
                validateDefinitionAndResultsLen(
                        scenarioRun.getAfterHookResults(), scenarioDefinition.getAfterHooks().size(),
                        attributePattern, "afterHooks", validationConstraintViolations
                );
            }
        }

        if (!validationConstraintViolations.isEmpty()) {
            List<ErrorTo> errorTos = converter.convertList(validationConstraintViolations);

            throw new ValidationConstraintViolationException(errorTos);
        }
    }

    /**
     * Validate if definitions and results have same length.
     *
     * @param stepResults to obtain number of results
     * @param definitionsSize number of definitions
     * @param pattern to use for final validation message
     * @param attrName to use in validation message
     * @param validationConstraintViolations to add validation result in if needed
     */
    private void validateDefinitionAndResultsLen(List<StepResult> stepResults, int definitionsSize, String pattern,
                                                 String attrName, List<ValidationConstraintViolation> validationConstraintViolations) {
        if (stepResults.size() != definitionsSize) {
            String attribute = MessageFormat.format(pattern, attrName);
            ValidationConstraintViolation validationConstraintViolation
                    = createValidationResult(attribute, "definitionsAndResults.len.differ");
            validationConstraintViolations.add(validationConstraintViolation);
        }
    }

    /**
     * Create validation result.
     *
     * @param attribute where validation error occurred
     * @param code of validation
     */
    private ValidationConstraintViolation createValidationResult(String attribute, String code) {
        ValidationConstraintViolation validationConstraintViolation = new ValidationConstraintViolation();

        validationConstraintViolation.setAttribute(attribute);
        validationConstraintViolation.setCode(code);

        return validationConstraintViolation;
    }
}
