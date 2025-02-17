package com.reportportal.ta.aspects;

import com.reportportal.ta.attachments.AllureAttachment;
import com.reportportal.ta.attachments.ReportPortalAttachment;
import com.reportportal.ta.services.attachement.AttachmentService;
import com.reportportal.ta.services.reportportal.ReportPortalLaunchService;
import com.reportportal.ta.services.reportportal.ReportPortalLogHelper;
import com.reportportal.ta.services.reportportal.ReportPortalTestItemService;
import com.reportportal.ta.webdriver.WebDriverWrapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class StepLoggerAspect {

    @Value("${com.reportportal.ta.ui.selenoid.enable.video:false}")
    private boolean enableSelenoidVideo;

    @Value("${rp.enable:false}")
    private boolean enableReportPortal;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    AllureAttachment allureResource;

    @Autowired
    ReportPortalAttachment reportPortalResource;

    @Autowired
    private ReportPortalTestItemService reportPortalTestItemService;

    @Autowired
    private ReportPortalLaunchService reportPortalLaunchService;

    @Autowired
    private ReportPortalLogHelper reportPortalLogHelper;

    @Autowired
    WebDriverWrapper driver;

    @Pointcut("within(com.reportportal.ta.steps..*)")
    public void addScreenShot() {
        //method for Pointcut
    }

    @AfterThrowing(pointcut = "addScreenShot()", throwing = "exception")
    public void onThrowingExceptionAddScreenshotToTheCurrentStep() {
        attachmentService.attachScreenshot(allureResource);
        attachmentService.attachScreenshot(reportPortalResource);

        if (enableSelenoidVideo && enableReportPortal) {
            String launchUuid = reportPortalLaunchService.getCurrentLaunchUuid();
            String itemUuid = reportPortalTestItemService.getCurrentStepTestItemUuid();

            reportPortalLogHelper.storeItem(driver.getSessionId(), launchUuid, itemUuid);
        }
    }

    @Pointcut("within(com.reportportal.ta.steps..*)")
    public void logEachStep() {
    }

    @Before("logEachStep()")
    public void beforeStep() {
        log.info("Cucumber step...");
    }
}
