Feature: User can search and filter tips by tags

  Scenario: When user searches for a tag in the search bar, results are shown
    Given user is at the main page
    When "searchbar" is clicked
    And "Testtag" is typed in
    Then "Otm reading tip" is shown

  Scenario: When user searches for non-existing tags, no results are shown
    Given user is at the main page
    When "searchbar" is clicked
    And "wgjorhjortehnjrlwgmwgrgkegbee" is typed in
    Then "Otm reading tip" is not shown

  Scenario: When user hides read tags, none are shown
    Given user is at the main page
    When form is filled with title "A2" description "aa" url "https://github.com/mluukkai" tags "tage" and is submitted
    And "read" is clicked
    And "Mark as unread" is shown
    When "hideBox" is clicked
    Then "Mark as unread" is not shown

  Scenario: When user hides and then reveals read tags, they are shown again
    Given user is at the main page
    When form is filled with title "A1" description "aa" url "https://github.com/mluukkai" tags "tage" and is submitted
    And "read" is clicked
    And "Mark as unread" is shown
    When "hideBox" is clicked
    And "Mark as unread" is not shown
    When "hideBox" is clicked
    Then "Mark as unread" is shown

  