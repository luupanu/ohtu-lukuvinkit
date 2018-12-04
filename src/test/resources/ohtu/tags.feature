Feature: User can add tags

  Scenario: When all other fields but tags are empty and submit is clicked index is returned with errors
    Given user is at the main page
    When form is filled with tags "tagfortest3, tagfortest4"
    And new tip is submitted
    Then error "may not be empty" is shown

  Scenario: When all fields including tags are filled and submit is clicked new tip is added
    Given user is at the main page
    When form is filled with title "Otm vinkki" description "Vinkki vinkki" url "https://github.com/mluukkai" tags "tagfortest, tagfortest2"
    And new tip is submitted
    Then "tagfortest" is shown
    And "tagfortest2" is shown

  Scenario: When all fields and two tags with same name are filled and submit is clicked new tip is added with only one tag
    Given user is at the main page
    When form is filled with title "Vinkki" description "Deskriptio" url "https://github.com/mluukkai" tags "sametag, sametag, sametag"
    And new tip is submitted
    Then only one "sametag" is shown
