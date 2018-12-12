Feature: User can filter tips by read

Scenario: When user hides read tags, none are shown
    Given user is at the main page
    When form is filled with title "A2" description "aa" url "https://github.com/mluukkai" tags "tage"
    And new tip is submitted
    And "read" is clicked
    And "Mark as unread" is shown
    When "hideRead" is clicked
    Then "Mark as unread" is not shown

  Scenario: When user hides and then reveals read tags, they are shown again
    Given user is at the main page
    When form is filled with title "A1" description "aa" url "https://github.com/mluukkai" tags "tage"
    And new tip is submitted
    And "read" is clicked
    And "Mark as unread" is shown
    When "hideRead" is clicked
    And "Mark as unread" is not shown
    When "hideRead" is clicked
    Then "Mark as unread" is shown
