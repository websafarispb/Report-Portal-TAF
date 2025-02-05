package com.reportportal.ta.webdriver;

import static java.lang.String.valueOf;

import java.io.File;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

//@Component
//@Lazy
@Slf4j
public class WebDriverWrapper implements WebDriver {

    private static final String LOG_EXECUTION_TIME = "{} Execution_Time(ms):; {}; Page_URL:; {};By:; {}";

    private final WebDriver driver;

    public WebDriverWrapper(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getWrappedDriver() {
        return driver;
    }

    @Override
    public void get(String url) {
        log.debug("Opening url: {}", url);
        long startTime = System.currentTimeMillis();
        try {
            driver.get(url);
        } finally {
            log.info(LOG_EXECUTION_TIME, "Open_Page", getExecutionTime(startTime), url);
        }
    }

    @Override
    public @Nullable String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public @Nullable String getTitle() {
        return driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        log.debug("Finding elements by: {}", by);
        long startTime = System.currentTimeMillis();
        try {
            return driver.findElements(by);
        } finally {
            log.info(LOG_EXECUTION_TIME, "Find_Elements", getExecutionTime(startTime), driver.getCurrentUrl(), by);
        }
    }

    @Override
    public WebElement findElement(By by) {
        log.debug("Finding element by: {} ", by);
        long startTime = System.currentTimeMillis();
        try {
            return driver.findElement(by);
        } finally {
            log.info(LOG_EXECUTION_TIME, "Find_Element", getExecutionTime(startTime), driver.getCurrentUrl(), by);
        }
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        log.debug("Driver close");
        long startTime = System.currentTimeMillis();
        try {
            driver.close();
        } finally {
            log.info(LOG_EXECUTION_TIME, "Driver_Close", getExecutionTime(startTime), "");
        }
    }

    @Override
    public void quit() {
        log.debug("Driver quit");
        long startTime = System.currentTimeMillis();
        try {
            driver.quit();
        } finally {
            log.info(LOG_EXECUTION_TIME, "Driver_Quit", getExecutionTime(startTime), "");
        }
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public Options manage() {
        return driver.manage();
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
     * Take screenshot of the page.
     *
     * @return byte array
     */
    public byte[] takeScreenshotAsByteArray() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public File takeScreenshotAsFile() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }
}
