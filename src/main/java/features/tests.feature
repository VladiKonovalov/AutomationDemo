Feature: open and test the website

  Background:
    Given open the browser
    And navigate to tested website


  Scenario Outline: try login with not existed user
    Given press on <buttonName> button
    When enter invalid username and password
    Then got an error message

    Examples:
      | buttonName |
      | 'Login'    |


  Scenario Outline: try login with existing user
    Given press on <buttonName> button
    When login with <username> and <password>
    Then got a welcome text for <username>

    Examples:
      | buttonName |username|password|
      | 'Login'    |'a'       |'a'|


  Scenario Outline: User check one of the categories
    When the user navigates to the 'category' category page
    Examples:
      | category |
      | Laptops   |

