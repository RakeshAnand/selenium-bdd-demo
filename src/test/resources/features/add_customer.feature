@ui @Regression
Feature: Add new customer

  Background:
    Given the user is on the login page
    When the user enters valid credentials
    And clicks the login button
    Then the user should see the dashboard page

  Scenario: Add a new customer with valid details
    Given the user opens the add customer page
    When the user enters the following customer details:
      | name   | gender | dob        | address       | city      | state     | pin    | mobile     | email  | password |
      | Deepak | male   | 1990-05-15 | 12 MG Road    | Bengaluru | Karnataka | 560001 | 9876543210 | random | Test@123 |
    And the user submits the customer form
    Then the customer should be registered successfully
    And the generated customer id should be displayed