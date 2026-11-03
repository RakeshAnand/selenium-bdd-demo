@api 
@ignore
Feature: Verify User Creation via API

  Scenario Outline: Create multiple users successfully and validate responses
    Given I use the endpoint "https://jsonplaceholder.typicode.com"
    When I send a POST request to "/users" with name "<name>" and job "<job>"
    Then the response status code should be 201
    And the response should contain the created user details

    Examples: 

      | name     | job                 |
      | John Doe | Automation Engineer |
      | Alice    | Developer           |
      | Bob      | QA Lead             |



