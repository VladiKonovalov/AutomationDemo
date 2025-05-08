Feature: open and test the website

  Background:
    Given open the browser
    And navigate to tested website


  Scenario Outline: try login with not existed user or wrong password
    Given press on '<buttonName>' button
    When login with '<username>' and '<password>'
    Then got an alert with right '<message>'

    Examples:
      | buttonName | username | password | message             |
      | Login      | random   | random   | user does not exist |
      | Login      | a        | random   | 'wrong password'    |


  Scenario Outline: try login with existing user and right password
    Given press on '<buttonName>' button
    When login with '<username>' and '<password>'
    Then got a welcome text for '<username>'

    Examples:
      | buttonName | username | password |
      | Login      | a        | a        |


  Scenario Outline: a new User sing in
    Given press on '<buttonName>' button
    When sign in a new user
    Then got an alert with right '<message>'

    Examples:
      | buttonName | message             |
      | Sign up    | Sign up successful. |

  Scenario Outline: a User put in card item
    Given clicks on '<category>'
    And press on product number <product>
    Given press on '<buttonName>' button
    Then got an alert with right '<message>'
    And verify it exist in
    Examples:
      | category | product | buttonName  | message       |
      | Monitors | 2       | Add to card | product added |
