package com.reportportal.ta.hooks;

import com.reportportal.ta.services.reportportal.ReportPortalLogHelper;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CucumberEventListener implements EventListener {

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestRunFinished.class, event -> {
            log.info("CucumberEventListener: attaching video of failed test to the ReportPortal");

            ReportPortalLogHelper reportPortalLogHelper = ReportPortalLogHelper.getInstance();
            reportPortalLogHelper.logAllItems();
            ReportPortalLogHelper.cleanUp();
        });
    }
}