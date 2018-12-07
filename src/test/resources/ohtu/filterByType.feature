Feature: User can filter tips by type

Scenario: When user hides Link tips, they are not shown anymore
    Given user is at the main page
    When type "Link" is selected in the form
    And form is filled with title "Link tip for filter test" description "I love links" url "https://github.com/mluukkai" tags "Link_me_up"
    And new tip is submitted
    And "Link tip for filter test" is shown
    When "hideLinks" is clicked
    Then "Link tip for filter test" is not shown

Scenario: When user hides Book tips, they are not shown anymore
    Given user is at the main page
    When type "Book" is selected in the form
    And form is filled with title "Book tip for filter testing" description "I love books" author "Bookspirit" isbn "B-0-0-K" tags "Books_please"
    And new tip is submitted
    And "Book tip for filter test" is shown
    When "hideBooks" is clicked
    Then "Book tip for filter test" is not shown

Scenario: When user hides Article tips, they are not shown anymore
    Given user is at the main page
    When type "Article" is selected in the form
    And form is filled with title "Article tip for filter test" description "I love articles" author "Articlelover" tags "I_need_that_article"
    And new tip is submitted
    And "Article tip for filter test" is shown
    When "hideArticles" is clicked
    Then "Article tip for filter test" is not shown

Scenario: When user hides tips of all types, none are shown
    Given user is at the main page
    When "hideArticles" is clicked
    When "hideLinks" is clicked
    When "hideBooks" is clicked
    Then "Link tip for filter test" is not shown
    Then "Article tip for filter test" is not shown
    Then "Book tip for filter test" is not shown

Scenario: When user hides and rehides tips of all types, all are shown.
    Given user is at the main page
    When "hideArticles" is clicked
    When "hideLinks" is clicked
    When "hideBooks" is clicked
    And "Link tip for filter test" is not shown
    And "Article tip for filter test" is not shown
    And "Book tip for filter test" is not shown
    When "hideArticles" is clicked
    When "hideLinks" is clicked
    When "hideBooks" is clicked
    Then "Link tip for filter test" is shown
    Then "Article tip for filter test" is shown
    Then "Book tip for filter test" is shown