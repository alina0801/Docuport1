
Feature: Docuport Login Logout Feature

  Background:
    Given user is on Docuport login page
  @test
  Scenario Outline: Login and out
    When user enters username for "<userType>"
    And user enters password for "<userType>"
    And user clicks "login" button
    Then user should see the home page for "<userType>"
    When user clicks "user icon" button
    And user clicks "logout" button
    Then the user return to the login page
    Examples:
      | userType   |
      | client     |
      | supervisor |
      | advisor    |
      | employee   |