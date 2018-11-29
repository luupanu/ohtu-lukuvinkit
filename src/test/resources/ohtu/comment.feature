Feature: User can write comments to old and newly created reading tips

  Scenario: When new reading tip is added, it contains a comments link
    Given user is at the main page
    When form is filled with title "Otm reading tip" description "Nice tip here" url "https://github.com/mluukkai" tags "Testtag" and is submitted
    Then link "0 comment(s)" is shown

  Scenario: Comment can be added to a newly created reading tip
    Given user is at the main page
    When form is filled with title "Otm reading tip" description "Nice tip here" url "https://github.com/mluukkai" tags "Testtag" and is submitted
    And link "0 comment(s)" is clicked
    Then comment "Dope tip" is submitted
    And link "1 comment(s)" is shown