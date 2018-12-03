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