package com.reportportal.ta.aspects;

import com.reportportal.ta.attachments.AllureAttachment;
import com.reportportal.ta.attachments.ReportPortalAttachment;
import com.reportportal.ta.services.attachement.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class StepLoggerAspect {

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    AllureAttachment allureResource;

    @Autowired
    ReportPortalAttachment reportPortalResource;

    @Pointcut("within(com.reportportal.ta.steps..*)")
    public void addScreenShot() {
        //method for Pointcut
    }

    @AfterThrowing(pointcut = "addScreenShot()", throwing = "exception")
    public void onThrowingExceptionAddScreenshotToTheCurrentStep() {
        attachmentService.attachScreenshot(allureResource);
        attachmentService.attachScreenshot(reportPortalResource);
    }

    @Pointcut("within(com.reportportal.ta.steps..*)")
    public void logEachStep() {
    }

    @Before("logEachStep()")
    public void beforeStep() {
        log.info("Cucumber step...");
    }
}
