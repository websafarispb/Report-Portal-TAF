package com.reportportal.ta.steps;

import static com.reportportal.ta.context.SharedTestContext.DASHBOARD_NAME;
import static com.reportportal.ta.utils.StringGenerator.generateRandomString;
import static java.lang.String.format;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ModelSteps extends AbstractSteps{

    @When("I fill name {string} and description {string} on add new dashboard form")
    public void fillAddNewDashboardForm(String dashboardName, String dashboardDescription) {
        allDashboardPage.fillDashboardForm(dashboardName, dashboardDescription);
    }

    @Then("I press add button on add new dashboard form")
    public void iPressAddButtonOnAddNewDashboardForm() {
        allDashboardPage.pressAddButton();
    }
}
