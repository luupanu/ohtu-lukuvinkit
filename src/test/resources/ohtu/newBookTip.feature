Feature: User can add a new reading tip (Book)

  @only
  Scenario: When all fields are filled and submit is clicked a new Book tip is added
    Given user is at the main page
    When type "Book" is selected in the form
    And form is filled with title "Sailing Alone Around the World" description "A book about sailing." author "Captain Joshua Slocum" isbn "0-486-20326-3" tags "Sailing" and is submitted
    Then "Sailing Alone Around the World" is shown
