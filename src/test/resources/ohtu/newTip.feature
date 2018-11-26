Feature: User can add a new reading tip

  Scenario: When all fields but tags are filled and submit is clicked new tip is added
    Given user is at the main page
    When form is filled with title "Otm vinkki" description "Vinkki vinkki" url "https://github.com/mluukkai" and is submitted
    Then "Otm vinkki" is shown

  Scenario: When all fields including tags are filled and submit is clicked new tip is added
    Given user is at the main page
    When form is filled with title "Otm vinkki" description "Vinkki vinkki" url "https://github.com/mluukkai" tags "tagfortest, tagfortest2" and is submitted
    Then "tagfortest" is shown
    And "tagfortest2" is shown

  Scenario: When one or more fields are filled incorrectly and submit is clicked index is returned with errors
    Given user is at the main page
    When form is not filled and is submitted
    Then error "may not be empty" is shown

  Scenario: When all other fields but tags are empty and submit is clicked index is returned with errors
    Given user is at the main page
    When form is filled with tags "tagfortest3, tagfortest4" and is submitted
    Then error "may not be empty" is shown