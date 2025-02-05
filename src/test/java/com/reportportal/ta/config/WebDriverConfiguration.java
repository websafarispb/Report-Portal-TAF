package com.reportportal.ta.config;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

import com.reportportal.ta.webdriver.WebDriverWrapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@Slf4j
public class WebDriverConfiguration {

    private static final String CHROME_SELENOID = "chrome_selenoid";
    private static final String CHROME = "chrome";
    private static final String FIREFOX_SELENOID = "firefox_selenoid";
    private static final String FIREFOX = "firefox";
    private static final String HEADLESS = "HEADLESS_BROWSER";

    @Value("${com.reportportal.ta.ui.browser.name:chrome}")
    private String browserName;

    @Value("${com.reportportal.ta.ui.selenoid.url:http://selenoid:4444}")
    private String selenoidUrl;

    @Value("${path.to.download.folder}")
    private String pathToDownloadFolder;

    @Value("${com.reportportal.ta.ui.selenoid.enable.vnc:false}")
    private boolean enableSelenoidVNC;

    @Value("${com.reportportal.ta.ui.selenoid.enable.video:false}")
    private boolean enableSelenoidVideo;

    @Value("${com.reportportal.ta.ui.url}")
    private String url;

    @Value("${com.reportportal.ta.ui.webdriver.explicit.timeout.millis}")
    private Integer timeOut;

    private final Environment env;

    public WebDriverConfiguration(Environment env) {
        this.env = env;
    }

    @Bean(destroyMethod = "quit")
    public WebDriverWrapper webDriver() {
        log.debug("Start initialization browser: {}", browserName);
        //return initSelenoidChrome();

        return Match(browserName).of(
            Case($(CHROME::equalsIgnoreCase), this::initChrome),
       //     Case($(FIREFOX::equalsIgnoreCase), this::initFirefox),
            Case($(CHROME_SELENOID::equalsIgnoreCase), this::initSelenoidChrome)
      //      Case($(FIREFOX_SELENOID::equalsIgnoreCase), this::initSelenoidFirefox)
        );
    }

    private WebDriverWrapper initChrome() {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory",
            FilenameUtils.separatorsToSystem(env.getProperty("user.dir") + pathToDownloadFolder));
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(
            "--ignore-certificate-errors",
            "--incognito");

        if (Boolean.parseBoolean(env.getProperty(HEADLESS))) {
            chromeOptions.addArguments("--disable-gpu", "--window-size=1920x1080", "--headless");
        }
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.manage().window().maximize();
        return new WebDriverWrapper(driver);
    }


    public  WebDriverWrapper initSelenoidChrome() {
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
            "enableVNC", enableSelenoidVNC,
            "enableVideo", enableSelenoidVideo
        ));
        chromeOptions.addArguments(
            "--no-sandbox",
            "--ignore-certificate-errors",
            "--incognito");

        RemoteWebDriver remoteWebDriver = null;

        try {
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
