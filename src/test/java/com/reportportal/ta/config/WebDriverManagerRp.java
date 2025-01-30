package com.reportportal.ta.config;

import com.reportportal.ta.webdriver.WebDriverWrapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverManagerRp {

    private static WebDriverWrapper webDriverWrapper;

    public static WebDriverWrapper getWebDriverWrapper() {
        if (webDriverWrapper == null) {
            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            webDriverWrapper = new WebDriverWrapper(driver);
        }
        return webDriverWrapper;
    }

    public static void quitDriver() {
        if (webDriverWrapper != null) {
            webDriverWrapper.quit();
            webDriverWrapper = null;
        }
    }
}
