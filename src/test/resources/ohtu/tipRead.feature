Feature: User can mark tips as read

  Scenario: User can toggle a tip as read
    Given user is at the main page
    When "read" is clicked
    Then "READ" is shown
    And "Mark as unread" is shown