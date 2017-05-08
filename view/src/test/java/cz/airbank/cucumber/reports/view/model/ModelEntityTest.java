package cz.airbank.cucumber.reports.view.model;

import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;

import org.junit.Test;

/**
 * Tests all POJOs in "cz.airbank.cucumber.reports.view.model" package.
 * This class is necessary because JSF calls the model getters automatically and cannot be unit tested.
 *
 * @author David Passler
 */
public class ModelEntityTest {

    /**
     * The package to test.
     */
    private static final String PACKAGE_TO_TEST = "cz.airbank.cucumber.reports.view.model";

    @Test
    public void validate() {
        Validator validator = ValidatorBuilder.create()
            .with(new GetterMustExistRule())
            .with(new GetterTester())
            .build();
        validator.validate(PACKAGE_TO_TEST);
    }

}
