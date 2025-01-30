Feature: Example Feature RP

  Scenario Outline: Open a RP page

    Given I open the ReportPortal login page
    When I login on Report Portal page
    And I press add new dashboard button
    And I fill name '<Dashboard name>' and description '<Dashboard description>' on add new dashboard form
    Then I press add button on add new dashboard form
    And I wait dashboard with name '<Dashboard name>' is created
    And I open all dashboard page
    And I check that dashboard with name '<Dashboard name>' is displayed
    And I check that dashboard with description '<Dashboard description>' is displayed
    Examples:
      | Dashboard name    | Dashboard description                                      |
      | AT dashboard name | This is dashboard was created by test automation framework |
