Feature: Used to test propagation of parameters which are declared in annotation to Cucumber report API

  Scenario Outline:
    Given Resident applicant with phone number "<PHONE_NUMBER>"

  Examples:
  | PHONE_NUMBER  |
  | +917######### |