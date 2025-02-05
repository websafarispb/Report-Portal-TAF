package com.reportportal.ta.services.attachement;

import com.reportportal.ta.annotations.PageObject;
import com.reportportal.ta.attachments.AttachmentResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@PageObject
@Slf4j
@Component
public class AttachmentService {

    public void attachScreenshot(AttachmentResource resource) {
        resource.attachScreenShot();
    }

}

