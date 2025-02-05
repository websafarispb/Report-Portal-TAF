package com.reportportal.ta.attachments;

import com.epam.reportportal.message.ReportPortalMessage;
import com.epam.reportportal.service.ReportPortal;
import com.reportportal.ta.annotations.PageObject;
import java.io.IOException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@PageObject
@Slf4j
@Component
public class ReportPortalAttachment extends AbstractAttachment implements AttachmentResource {

    @Override
    public void attachScreenShot() {
        try {
            ReportPortalMessage message = new ReportPortalMessage(driver.takeScreenshotAsFile(),
                "Browser screenshot");
            ReportPortal.emitLog(message, "info", new Date());
            log.info("Screenshot sent to Report Portal.");
        } catch (IOException e) {
            log.error("Error while uploading the screenshot to the ReportPortal", e);
        }
    }
}
