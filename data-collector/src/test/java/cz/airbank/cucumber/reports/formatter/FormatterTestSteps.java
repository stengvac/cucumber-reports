package cz.airbank.cucumber.reports.formatter;

import org.junit.Assert;

import java.util.List;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


/**
 * Serves only for Cucumber .feature formatter tests.
 *
 * @author David Passler
 */
public class FormatterTestSteps {

    @Before
    public void setUp() {
        //for testing purposes - so cucumber engine will call before method
        // and hook is included in reported feature
    }

    @Given("^(Resident|Non-resident) applicant with phone number \"([^\"]*)\"$")
    public void applicantWithPhoneNumber(String clientType, String format) throws Throwable {
    }


    @Given("^I started my calculation$")
    public void iStartedMyCalculation() throws Throwable {
    }

    @When("^I execute operation \"([^\"]*)\" on \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iExecuteOperationOnAnd(String operation, String firstNum, String secondNum) throws Throwable {
    }

    @Then("^The result is \"([^\"]*)\"$")
    public void theResultIs(int expected) throws Throwable {

    }

    @When("^I invoke pending method$")
    public void iInvokePendingMethod() throws Throwable {
        throw new PendingException();
    }

    @When("^Unused$")
    public void unused() throws Throwable {
        throw new PendingException();
    }

    @Given("^I have personal data$")
    public void iHavePersonalData(List<Person> persons) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Given("^I have another personal data$")
    public void iHaveAnotherPersonalData(List<Person> persons) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("^Skipped$")
    public void skipped() throws Throwable {
    }

    @When("^I execute  fail$")
    public void iExecuteFail() throws Throwable {
        Assert.fail();
    }

    public static class Person {
        String id, firstName, secondName;
    }

    @After
    public void tearDown() {
        //testing purposes - so cucumber engine will call after method
        // and hook is included in reported feature
    }
}
