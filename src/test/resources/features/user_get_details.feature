@api 
#@ignore
Feature: Filter and Print Dynamic User Details from API

  Scenario Outline: Fetch details for a specific user by name
    Given I use the endpoint "https://jsonplaceholder.typicode.com"
    When I send a GET request to "/users"
    Then the response status code should be 200
    And I extract and print the detail "<detail_path>" for the user named "<target_name>"

    Examples:

      | target_name     | detail_path          |
      | Leanne Graham   | email                |
      | Ervin Howell    | address.city         |
      | Clementina Bauch| company.catchPhrase  |
      | Patricia Lebsack| phone                |

@ignore
  Scenario Outline: Fetch details for a specific user by name
    Given I use the endpoint "https://jsonplaceholder.typicode.com"
    When I send a GET request to "/users"
    Then the response status code should be 200
    #And I extract and print the detail using POJO for the user named "<target_name>"
    #And I extract and print the address and company details for the user named "<target_name>"
        And print the detail of user named "<target_name>"

    Examples:

      | target_name     | detail_path          |
      #| Leanne Graham   | email                |
      #| Ervin Howell    | address.city         |
      | Clementina Bauch| company.catchPhrase  |
      #| Patricia Lebsack| phone                |



