Feature: User can add a new reading tip

  Scenario: When all fields but tags are filled and submit is clicked new tip is added
    Given user is at the main page
    When form is filled with title "Otm reading tip" description "Nice tip here" url "https://github.com/mluukkai" tags "Testtag" and is submitted
    Then "Otm reading tip" is shown

  Scenario: When url field is filled inncorrectly and other fields are filled correctly and submit is clicked
    Given user is at the main page
    When form is filled with title "Otm reading tip" description "Nice tip here" url "github.com/mluukkai" tags "Testtag" and is submitted
    Then error "must be a valid URL" is shown