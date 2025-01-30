package com.reportportal.ta.config;

import com.reportportal.ta.webdriver.WebDriverWrapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class WebDriverConfiguration {

    private String browserName = "chrome";

    @Bean(destroyMethod = "quit")
    public WebDriverWrapper webDriver() {
        log.debug("Start initialization browser: {}", browserName);

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return new WebDriverWrapper(driver);
    }
}
