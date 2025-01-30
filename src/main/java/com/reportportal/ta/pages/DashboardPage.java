package com.reportportal.ta.pages;

import com.reportportal.ta.annotations.PageObject;
import com.reportportal.ta.pages.components.AddNewDashboardModalWindow;
import com.reportportal.ta.pages.components.DashboardTable;
import com.reportportal.ta.webdriver.WebDriverHelper;
import com.reportportal.ta.webdriver.WebDriverWrapper;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

//@Slf4j
//@Component
@PageObject
public class DashboardPage extends AbstractBasePage{

    private static final int TIME_OUT = 4000;
    private static final int timeout = 30000;
    private static final String UNABLE_TO_FIND_WEB_ELEMENT = "UNABLE_TO_FIND_WEB_ELEMENT";

    @FindBy(xpath = "//button[@type='button' and .//span[text()='Add New Dashboard']]")
    WebElement addNewDashboardButton;
    private static String DASHBOARD_HEADER_NAME_XPATH = "//span[text()='%s']";

    @Autowired
    @Lazy
    AddNewDashboardModalWindow addNewDashboardModalWindow;
    @Autowired
    @Lazy
    DashboardTable dashboardTable;

    public void waitHeaderDashboardNameElementIsVisible(String dashboardName) {
        By dashboardNameLocator = By.xpath(DASHBOARD_HEADER_NAME_XPATH.formatted(dashboardName));
        driverHelper.waitForElementIsVisible(dashboardNameLocator);
    }

    public void pressAddNewDashboardButton() {
        driverHelper.waitForElementIsVisible(addNewDashboardButton);
        driverHelper.click(addNewDashboardButton);
    }

    public void closeAddNewDashboardModalWindow() {
        addNewDashboardModalWindow.isDisplayed();
        addNewDashboardModalWindow.close();
    }

    public void fillDashboardForm(String name, String description) {
        addNewDashboardModalWindow.fillDashboardForm(name, description);
    }

    public void pressAddButton() {
        addNewDashboardModalWindow.clickAddButton();
    }

    public String getDashboardId() {
        String [] dashboardIdMas = webDriverWrapper.getCurrentUrl().split("/");
        return dashboardIdMas[6];
    }
}
