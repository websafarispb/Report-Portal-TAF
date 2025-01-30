package com.reportportal.ta.steps;

import static com.reportportal.ta.context.DeletionTestContext.DASHBOARD_IDS;

import io.cucumber.java.en.When;

//@SpringBootTest
public class ActionsSteps extends AbstractSteps {

    @When("I open the ReportPortal login page")
    public void openReportPortalLoginPage() {
        loginPage.openPage();
    }

    @When("I login on Report Portal page")
    public void loginOnReportPortalPage() {
        loginPage.login("default");
        loginPage.password("1q2w3e");
        loginPage.submit();
    }

    @When("I press add new dashboard button")
    public void pressAddNewDashboardButton() {
        allDashboardPage.pressAddNewDashboardButton();
    }

    @When("I wait dashboard with name {string} is created")
    public void waitDashboardPageWithNameIsOpen(String dashboardName) {
        dashboardPage.waitHeaderDashboardNameElementIsVisible(dashboardName);
        var dashboardId = dashboardPage.getDashboardId();
        deletionTestContext.addObjectForDeletion(DASHBOARD_IDS, dashboardId);
    }

    @When("I open all dashboard page")
    public void openReportPortalAllDashboardPage() {
        allDashboardPage.openPage();
    }
}
