@db @Regression
Feature: H2 in-memory database operations

  Background:
    Given an H2 in-memory database with an employees table

  Scenario: Insert and retrieve a record
    When I insert an employee with id 1, name "Alice" and salary 50000
    Then the employee with id 1 should have name "Alice"

  Scenario: Insert multiple records from a table
    When I insert the following employees:
      | id | name  | salary |
      | 1  | Alice | 50000  |
      | 2  | Bob   | 60000  |
      | 3  | Carol | 70000  |
    Then the total number of employees should be 3

  Scenario: Update a record
    Given I insert an employee with id 1, name "Alice" and salary 50000
    When I update the salary of employee 1 to 55000
    Then the employee with id 1 should have salary 55000

  Scenario: Delete a record
    Given I insert an employee with id 1, name "Alice" and salary 50000
    When I delete the employee with id 1
    Then the total number of employees should be 0

  Scenario Outline: Query employees above a salary threshold
    Given I insert the following employees:
      | id | name  | salary |
      | 1  | Alice | 50000  |
      | 2  | Bob   | 60000  |
      | 3  | Carol | 70000  |
    Then <count> employees should earn more than <threshold>

    Examples:
      | threshold | count |
      | 40000     | 3     |
      | 55000     | 2     |
      | 80000     | 0     |