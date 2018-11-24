Feature: User can add a new reading tip

  Scenario: When all fields are filled properly and submit is clicked new tip is added
    Given user is at the main page
    When form is filled with title "Otm vinkki" description "Vinkki vinkki" url "https://github.com/mluukkai" and is submitted
    Then "Otm vinkki" is shown

  Scenario: When one or more fields are filled incorrectly and submit is clicked index is returned
    Given user is at the main page
    When form is not filled and is submitted
    Then error "may not be empty" is shown