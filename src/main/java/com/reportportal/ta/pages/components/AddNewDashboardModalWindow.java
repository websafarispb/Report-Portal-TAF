package com.reportportal.ta.pages.components;

import com.reportportal.ta.annotations.PageObject;
import com.reportportal.ta.webdriver.WebDriverHelper;
import com.reportportal.ta.webdriver.WebDriverWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@PageObject
public class AddNewDashboardModalWindow {

    @Autowired
    @Lazy
    private WebDriverWrapper webDriverWrapper;
    @Autowired
    @Lazy
    private WebDriverHelper driverHelper;

    @FindBy(xpath = "//div[contains(@class, 'modalLayout__modal-window')]")
    private WebElement modalElement;

    @FindBy(css = ".modalHeader__close-modal-icon--VeAZ5")
    private WebElement closeButton;

    @FindBy(xpath = "//button[text()='Add']")
    private WebElement addButton;

    @FindBy(xpath = "//button[text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//input[@placeholder='Enter dashboard name']")
    private WebElement dashBoardName;

    @FindBy(xpath = "//textarea[@placeholder='Enter dashboard description']")
    private WebElement dashBoardDescription;

    public boolean isDisplayed() {
        return modalElement.isDisplayed();
    }

    public void close() {
        closeButton.click();
    }

    public void clickAddButton() {
        if (isDisplayed()) {
            driverHelper.click(addButton);
        } else {
            throw new IllegalStateException("Modal window is not displayed!");
        }
    }

    public void clickCancelButton() {
        if (isDisplayed()) {
            driverHelper.click(cancelButton);
        } else {
            throw new IllegalStateException("Modal window is not displayed!");
        }
    }

    public void fillDashboardForm(String name, String description) {
        driverHelper.sendKeys(dashBoardName, name);
        driverHelper.sendKeys(dashBoardDescription, description);
    }
}

