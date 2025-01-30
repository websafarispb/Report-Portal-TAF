package com.reportportal.ta.steps;

import com.reportportal.ta.config.WebDriverManagerRp;
import com.reportportal.ta.context.DeletionTestContext;
import com.reportportal.ta.context.SharedTestContext;
import com.reportportal.ta.pages.AllDashboardPage;
import com.reportportal.ta.pages.LoginPage;
import com.reportportal.ta.pages.DashboardPage;
import com.reportportal.ta.services.api.ReportPortalDashboardService;
import com.reportportal.ta.webdriver.WebDriverWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class AbstractSteps {

    @Autowired
    @Lazy
    WebDriverWrapper webDriverWrapper;
    @Autowired
    @Lazy
    LoginPage loginPage;
    @Autowired
    @Lazy
    AllDashboardPage allDashboardPage;
    @Autowired
    @Lazy
    DashboardPage dashboardPage;
    @Autowired
    SharedTestContext sharedTestContext;
    @Autowired
    DeletionTestContext deletionTestContext;

    @Autowired
    @Lazy
    ReportPortalDashboardService reportPortalDashboardService;
}
