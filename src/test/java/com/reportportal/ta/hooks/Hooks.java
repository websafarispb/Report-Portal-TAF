package com.reportportal.ta.hooks;

import com.reportportal.ta.config.WebDriverManagerRp;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void setUp() {
        //WebDriverManagerRp.getWebDriverWrapper();
    }

    @After
    public void tearDown() {
      //  WebDriverManagerRp.quitDriver();
    }
}
