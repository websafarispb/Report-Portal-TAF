package com.reportportal.ta.pages;

import com.reportportal.ta.annotations.PageObject;
import com.reportportal.ta.webdriver.WebDriverHelper;
import com.reportportal.ta.webdriver.WebDriverWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

//@Slf4j
@PageObject
public class LoginPage extends AbstractBasePage {

    //private String url = "http://localhost:8080/";
    private String url = "http://host.docker.internal:8080/";

    @FindBy(name = "login")
    private WebElement rpLoginField;

    @FindBy(name = "password")
    private WebElement rpPasswordField;

    @FindBy(xpath = "//button[text()='Login']")
    private WebElement submitButton;

    public void openPage() {
        webDriverWrapper.navigate().to(url);
    }

    public void submit() {
        driverHelper.waitForElementIsVisible(submitButton);
        driverHelper.click(submitButton);
    }

    public void login(String username) {
        driverHelper.waitForElementIsVisible(rpLoginField);
        driverHelper.waitForElementToBeClickable(rpLoginField);
        driverHelper.sendKeys(rpLoginField, username);
    }

    public void password(String password) {
        driverHelper.waitForElementIsVisible(rpPasswordField);
        driverHelper.sendKeys(rpPasswordField, password);
    }
}
