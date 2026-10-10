Feature: User Login Functionality
    As a registered user of the application
    I want to log into my account
    So that I can access my personalized dashboard

    Background:
        Given the user is on the login page

    @Smoke @Regression
    Scenario: Successful login with valid credentials
        When the user enters username "mngr668298" and password "AsunUva"
        And clicks the login button
        Then the user should see the dashboard page

    @Negative @Regression @skip
    Scenario Outline: Unsuccessful login with invalid credentials
        When the user enters username "<username>" and password "<password>"
        And clicks the login button
        Then an error message "<error_message>" should be displayed

        Examples:

            | username   | password          | error_message                |
            | wrong_user | SecurePassword123 | Invalid username or password |
            | john_doe   | wrong_password    | Invalid username or password |
            |            | SecurePassword123 | Username cannot be empty     |
            | john_doe   |                   | Password cannot be empty     |