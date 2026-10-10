Feature: User Management API Operations
    As an API consumer
    I want to submit record payloads to the server
    So that new database rows are properly committed

    Scenario: Successfully insert a new user record via API
        Given the API base URI is "https://reqres.in"
        When I send a POST request to "/api/users" with name "John Doe" and job "Engineer"
        Then the response status code should be 201
        And the response body should contain the generated row field "id"
        And the database field "name" value should match "John Doe"
