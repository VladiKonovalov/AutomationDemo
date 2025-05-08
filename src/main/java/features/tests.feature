Feature: open and test the website

  Background:
    Given open the browser
    And navigate to tested website



    Scenario: try login with not existed user
      Given press on 'Login' button
      When enter invalid username and password


  Scenario: User check one of the categories
    When the user navigates to the 'category' category page
      |Phones|

