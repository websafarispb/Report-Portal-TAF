package com.reportportal.ta.config;

import com.reportportal.ta.webdriver.WebDriverWrapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class WebDriverConfiguration {

    private String browserName = "chrome";

    @Bean(destroyMethod = "quit")
    public WebDriverWrapper webDriver() {
        log.debug("Start initialization browser: {}", browserName);
//
//        WebDriverManager.chromedriver().setup();
//        WebDriver driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        return new WebDriverWrapper(driver);
        return initRemoteChromeDriver();
    }

    public  WebDriverWrapper intLocalChromeDriver() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return new WebDriverWrapper(driver);
    }


    public  WebDriverWrapper initRemoteChromeDriver() {
        WebDriver driver = null;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", Map.<String, Object>of(
            "profile.default_content_settings.popups", 0,
            "download.default_directory", "/home/selenium/Downloads",
            "download.prompt_for_download", false,
            "download.directory_upgrade", true,
            "safebrowsing.enabled", false,
            "plugins.always_open_pdf_externally", true,
            "plugins.plugins_disabled", new ArrayList<String>().add("Chrome PDF Viewer")));

        chromeOptions.setCapability("selenoid:options", Map.<String, Object>of(
            "enableVNC", true,
            "enableVideo", true
        ));
        chromeOptions.addArguments(
            "--no-sandbox",
            "--ignore-certificate-errors",
            "--incognito");

        RemoteWebDriver remoteWebDriver = null;

        try {
            String selenoidUrl = "http://localhost:4444";
            //String selenoidUrl = "http://selenoid:4444";
            log.debug("SELENOID URL is {}", selenoidUrl);
            driver = new RemoteWebDriver(
                URI.create(selenoidUrl + "/wd/hub").toURL(),
                chromeOptions
            );
            driver.manage().window().maximize();
        } catch (MalformedURLException e) {
            log.error("Error in chrome initialization. Error message: {}", e.getMessage(), e);
        }
        return new WebDriverWrapper(driver);
    }
}
