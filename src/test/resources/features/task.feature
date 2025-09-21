Feature: Task Management

  Scenario: Add a new task
    Given the task list is empty
    When I add a task named "Write Report"
    Then the task list should contain 1 task
    And the task list should include "Write Report"

  Scenario: Get all tasks
    Given I have added a task named "Read Book"
    And I have added a task named "Do Homework"
    When I request all tasks
    Then I should see 2 tasks
    And I should see "Read Book"
    And I should see "Do Homework"
