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

@PageObject
public class AllDashboardPage extends AbstractBasePage{

    private static final int TIME_OUT = 4000;
    private static final int timeout = 30000;
    private static final String UNABLE_TO_FIND_WEB_ELEMENT = "UNABLE_TO_FIND_WEB_ELEMENT";
    private String url = "http://localhost:8080/ui/#default_personal/dashboard";


    @FindBy(xpath = "//button[@type='button' and .//span[text()='Add New Dashboard']]")
    WebElement addNewDashboardButton;
    private static String DASHBOARD_NAME_XPATH = "//span[text()='%s']";

    @Autowired
    @Lazy
    AddNewDashboardModalWindow addNewDashboardModalWindow;
    @Autowired
    @Lazy
    DashboardTable dashboardTable;

    public void openPage() {
        webDriverWrapper.get(url);
    }

    public void waitDashboardNameElementIsVisible(String dashboardName) {
        By dashboardNameLocator = By.xpath(DASHBOARD_NAME_XPATH.formatted(dashboardName));
        driverHelper.waitForElementIsVisible(dashboardNameLocator);
    }

    public void pressAddNewDashboardButton() {
        driverHelper.waitForElementIsVisible(addNewDashboardButton);
        driverHelper.waitForElementToBeClickable(addNewDashboardButton);
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

    public boolean isDashboardWithNameDisplayed(String dashboardName) {
        return dashboardTable.getColumnValuesByHeaderName("Dashboard Name")
                             .stream()
                             .anyMatch(el -> el.equalsIgnoreCase(dashboardName));
    }

    public boolean isDashboardWitheDescriptionDisplayed(String dashboardDescription) {
        return dashboardTable.getRows()
                             .stream()
                             .map(r -> r.findElement(By.cssSelector(".dashboardTable__description--tHp7Q")))
                             .map(WebElement::getText)
                             .anyMatch(d -> d.equalsIgnoreCase(dashboardDescription));
    }
}
