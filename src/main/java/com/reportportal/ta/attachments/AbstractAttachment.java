package com.reportportal.ta.attachments;

import com.reportportal.ta.webdriver.WebDriverWrapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAttachment {

    @Autowired
    WebDriverWrapper driver;
}
