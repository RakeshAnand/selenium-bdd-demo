@api @ignore
Feature: API User Authentication

  Scenario: Successfully login with valid credentials and extract access token
    Given I hit the endpoint "https://reqres.in"
    When I send a POST request to "/api/login" with email "eve.holt@reqres.in" and password "cityslicka"
    #Then the response status code should be 200
    And the response should contain a valid accessToken
