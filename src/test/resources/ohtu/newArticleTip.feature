Feature: User can add a new reading tip (Article)

  Scenario: When all fields are filled and submit is clicked a new Article tip is added
    Given user is at the main page
    When type "Article" is selected in the form
    And form is filled with title "New article tip" description "Nice tip here" author "Author" tags "Testtag" and is submitted
    Then "New article tip" is shown
