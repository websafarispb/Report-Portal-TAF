package com.reportportal.ta.services.reportportal;

import com.reportportal.ta.model.reportportal.ReportPortalLogItem;
import com.reportportal.ta.services.SelenoidService;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReportPortalLogHelper {

    @Autowired
    private ReportPortalLogService reportPortalLogService;

    @Autowired
    private SelenoidService selenoidService;

    private static volatile ReportPortalLogHelper instance;

    private static final Map<String, Map<String, String>> items = new HashMap<>();

    private ReportPortalLogHelper() {
    }

    @Bean
    public static ReportPortalLogHelper getInstance() {
        if (instance == null) {
            synchronized (ReportPortalLogHelper.class) {
                if (instance == null) {
                    instance = new ReportPortalLogHelper();
                }
            }
        }
        return instance;
    }

    public static void cleanUp() {
        instance = null;
    }

    public void storeItem(String sessionId, String launchUuid, String itemUuid) {
        items.put(sessionId, Map.of("itemUuid", itemUuid, "launchUuid", launchUuid));
    }

    public void logAllItems() {
        if (items.isEmpty()) {
            log.info("There is no video to attach");
            return;
        }

        for (Map.Entry<String, Map<String, String>> entry : items.entrySet()) {
            String sessionId = null;

            try {
                sessionId = entry.getKey();
                String itemUuid = entry.getValue().get("itemUuid");
                String launchUuid = entry.getValue().get("launchUuid");

                log.info("Post log item to the ReportPortal: Session ID: {}; launchUuid: {}; itemUuid: {};", sessionId,
                    launchUuid, itemUuid);

                String message = "Selenoid video, sessionId: " + sessionId;
                File video = selenoidService.getSelenoidVideoFile(sessionId);

                ReportPortalLogItem logItem = new ReportPortalLogItem(itemUuid, launchUuid, message, "info",
                    new Date(), video);

                reportPortalLogService.postLogFile(logItem, video);
            } catch (Exception e) {
                log.error("Error while posting log item to the ReportPortal. Session ID: {} {}", sessionId,
                    e.getMessage(), e);
            }
        }
    }
}
