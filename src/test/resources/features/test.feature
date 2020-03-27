Feature: Write some automation tests

 Background: 
    Given User is on the home page
    When User clicks on the "Sign in" button
    Then User enters valid username and password
    And User clicks on the "Sign in Login" button
    Then User verifies that he is on the account page

  @MyAccount @Positive @Etsy @smoke @regression @ETSY-1234
  Scenario: Changing the user name
    When User clicks on the Your Account icon
    Then from the list picks the View your profile tab
    And User clicks on Edit Profile
    Then User clicks on Change or remove hyperlink
    And User enters the First Name and the Last Name
    Then User verifies that the name was changed

  @MyAccountNegative @Etsy @Negative @regression @ETSY-1234
  Scenario Outline: verifying fields error messages
    When User clicks on the Your Account icon
    Then from the list picks the View your profile tab
    And User clicks on Edit Profile
    Then User clicks on Change or remove hyperlink
    And User enters "<name>" into the field
    Then User verifies the "<error_message>"
    And User verifies that the Save_Button is disabled
    Examples: 
      | name       | error_message                                |
      | first_name | Your first name contains invalid characters. |
      | last_name  | Your last name contains invalid characters.  |