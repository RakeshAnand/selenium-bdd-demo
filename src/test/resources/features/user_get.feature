@api 
@ignore
Feature: Verify User Details via GET API

  Scenario: Retrieve user details successfully and validate fields
    Given I use the endpoint "https://jsonplaceholder.typicode.com"
    When I send a GET request to "/users/1"
    Then the response status code should be 200
    And the response should contain valid user profile information

