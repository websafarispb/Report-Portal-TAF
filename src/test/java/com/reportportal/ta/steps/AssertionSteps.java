package com.reportportal.ta.steps;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.cucumber.java.en.Then;

public class AssertionSteps extends AbstractSteps {

    @Then("I check that dashboard with name {string} is displayed")
    public void dashboardWithNameIsDisplayed(String dashboardName) {

        assertThat(allDashboardPage.isDashboardWithNameDisplayed(dashboardName))
            .as("Check if dashboard with name '%s' is displayed", dashboardName)
            .isTrue();
    }

    @Then("I check that dashboard with description {string} is displayed")
    public void dashboardWithDescriptionIsDisplayed(String dashboardDescription) {

        assertThat(allDashboardPage.isDashboardWitheDescriptionDisplayed(dashboardDescription))
            .as("Check if dashboard with description '%s' is displayed", dashboardDescription)
            .isTrue();
    }
}
