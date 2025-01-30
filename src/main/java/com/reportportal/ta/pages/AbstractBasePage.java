package com.reportportal.ta.pages;

import com.reportportal.ta.webdriver.WebDriverHelper;
import com.reportportal.ta.webdriver.WebDriverWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public abstract class AbstractBasePage {

    @Autowired
    @Lazy
    protected WebDriverHelper driverHelper;

    @Autowired
    @Lazy
    protected WebDriverWrapper webDriverWrapper;
}
