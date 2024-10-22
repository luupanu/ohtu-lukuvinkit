Feature: User can add a new reading tip (Link)

  Scenario: When all fields are filled and submit is clicked a new Link tip is added
    Given user is at the main page
    When form is filled with title "New link tip" description "Nice tip here" url "https://github.com/mluukkai" tags "Testtag"
    And new tip is submitted
    Then "New link tip" is shown

  Scenario: When url field is filled incorrectly and other fields are filled correctly and submit is clicked
    Given user is at the main page
    When form is filled with title "New link tip" description "Nice tip here" url "github.com/mluukkai" tags "Testtag"
    And new tip is submitted
    Then error "must be a valid URL" is shown

  Scenario: When all fields are filled but URL is left empty and submit is clicked
    Given user is at the main page
    When form is filled with title "New link tip" description "Nice tip here" url "" tags "Testtag"
    And new tip is submitted
    Then error "URL must not be empty!" is shown
