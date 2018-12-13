Feature: User can prioritise and change priorities of tips

  Scenario: User can change priorities of unread tips
    Given user is at the main page
    When form is filled with title "PriorityUnreadTip1" description "test" url "https://" tags "tag"
    And new tip is submitted
    And form is filled with title "PriorityUnreadTip2" description "test" url "https://" tags "tag"
    And new tip is submitted
    And "PriorityUnreadTip1" is dragged and dropped to "PriorityUnreadTip2"
    Then "PriorityUnreadTip2" has higher priority than "PriorityUnreadTip1"

  Scenario: User can change priorities of read tips
    Given user is at the main page
    When form is filled with title "PriorityReadTip1" description "test" url "https://" tags "tag"
    And new tip is submitted
    And "read" inside "PriorityReadTip1" is clicked
    And form is filled with title "PriorityReadTip2" description "test" url "https://" tags "tag"
    And new tip is submitted
    And "read" inside "PriorityReadTip2" is clicked
    And "PriorityReadTip1" is dragged and dropped to "PriorityReadTip2"
    Then "PriorityReadTip2" has higher priority than "PriorityReadTip1"

  Scenario: User cannot change priorities of tips with different read status
    Given user is at the main page
    When "PriorityUnreadTip2" is dragged and dropped to "PriorityReadTip2"
    Then "PriorityUnreadTip2" has higher priority than "PriorityReadTip2"
