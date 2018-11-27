Feature: User can comment selected tip and browse commentes if comments exists

  Scenario: Users can se comments
    Given user is at the main page
    When form is filled with title "Otm reading tip" description "Nice tip here" url "github.com/mluukkai" tags "Testtag" and is submitted
    Then "0 comment(s)" is shown

Scenario: Users can add comments
    Given user is at the main page
    When "0 comment(s)" is clicked
    And page contains "commentDescription" field
    Then comment "What a dope tip!" is submitted 
    And "1 comment(s)" is shown 