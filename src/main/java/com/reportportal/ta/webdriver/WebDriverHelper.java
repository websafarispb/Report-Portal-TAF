package com.reportportal.ta.webdriver;

import static java.lang.String.valueOf;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
//@Lazy
@Slf4j
public class WebDriverHelper {

    private static final int TIME_OUT = 4000;
    private static final int timeout = 30000;
    private static final String CLICK = "Click";
    public static final String UNABLE_TO_FIND_WEB_ELEMENT = "Unable to find web element '%s'";
    private static final String LOG_EXECUTION_TIME = "{} Execution_Time(ms):; {}; Page_URL:; {};";

    @Autowired
    @Lazy
    private WebDriverWrapper webDriverWrapper;

    public void sendKeys(WebElement webElement, String message) {
        long startTime = System.currentTimeMillis();
        try {
            waitForElementIsVisible(webElement);
            webElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
            webElement.sendKeys(message);
        } finally {
            log.info(LOG_EXECUTION_TIME, "Send_Keys", getExecutionTime(startTime), webDriverWrapper.getCurrentUrl());
        }
    }

    public boolean waitForElementIsVisible(WebElement webElement) {
        long startTime = System.currentTimeMillis();
        try {
            return new WebDriverWait(webDriverWrapper, Duration.ofSeconds(timeout / TIME_OUT)).until(visibilityOf(webElement));
        } catch (TimeoutException exception) {
            log.debug(String.format(UNABLE_TO_FIND_WEB_ELEMENT, webElement), exception);
            return false;
        } finally {
            log.info(LOG_EXECUTION_TIME, "Wait_For_Element_To_Be_Visible", getExecutionTime(startTime),
                webDriverWrapper.getCurrentUrl());
        }
    }

    public WebElement waitForElementIsVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(webDriverWrapper, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

//    public boolean waitForElementIsVisible(By locator) {
//        long startTime = System.currentTimeMillis();
//        try {
//            return new WebDriverWait(webDriverWrapper, Duration.ofSeconds(timeout / TIME_OUT))
//                .until(ExpectedConditions.visibilityOfElementLocated(locator));
//        } catch (TimeoutException exception) {
//            log.debug(String.format("Unable to find element located by '%s'", locator), exception);
//            return false;
//        } finally {
//            log.info(LOG_EXECUTION_TIME, "Wait_For_Element_To_Be_Visible", getExecutionTime(startTime),
//                webDriverWrapper.getCurrentUrl());
//        }
//    }

    /**
     * Check that web element is clickable.
     *
     * @param element - WebElement
     */
    public void waitForElementToBeClickable(WebElement element) {
        long startTime = System.currentTimeMillis();
        try {
            new WebDriverWait(webDriverWrapper.getWrappedDriver(), Duration.ofSeconds(timeout / TIME_OUT))
                .until(ExpectedConditions.elementToBeClickable(element));
        } finally {
            log.info(LOG_EXECUTION_TIME, "Wait_For_Element_To_Be_Clickable", getExecutionTime(startTime),
                webDriverWrapper.getCurrentUrl());
        }
    }

    /**
     * Get execution time of action.
     *
     * @param startTime - start time
     *
     * @return execution time im ms
     */
    private String getExecutionTime(long startTime) {
        return valueOf(System.currentTimeMillis() - startTime);
    }

    /**
     * An expectation for checking the element to be visible.
     *
     * @param element used to check its visibility
     *
     * @return Boolean true when elements is visible
     */
    private ExpectedCondition<Boolean> visibilityOf(final WebElement element) {
        return new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                return isVisible(element);
            }

            @Override
            public String toString() {
                return "visibility of " + element;
            }
        };
    }

    private boolean isVisible(final WebElement element) {
        try {
            return element.isDisplayed();
        } catch (StaleElementReferenceException ignored) {
            log.debug(String.format("HTML page source was changed for '%s'", element), ignored);
            return false;
        }
    }

    public void click(WebElement webElement) {
        long startTime = System.currentTimeMillis();
        try {
            waitForElementToBeClickable(webElement);
            webElement.click();
        } finally {
            log.info(LOG_EXECUTION_TIME, CLICK, getExecutionTime(startTime), webDriverWrapper.getCurrentUrl());
        }
    }
}
