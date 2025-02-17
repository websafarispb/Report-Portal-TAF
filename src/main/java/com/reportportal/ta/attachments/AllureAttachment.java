package com.reportportal.ta.attachments;

import io.qameta.allure.Allure;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AllureAttachment extends AbstractAttachment implements AttachmentResource {

    private static final String DATE_TIME_FORMAT = "dd-MMM-yyyy_hh:mm:ss";

    @Autowired
    private Environment environment;

    @Override
    public void attachScreenShot() {
        try {
            byte[] screenShot = driver.takeScreenshotAsByteArray();
            Allure.getLifecycle()
                  .addAttachment(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
                      "image/png", "png", screenShot);
        } catch (Exception e) {
            log.error("Error while uploading the screenshot to the Allure", e);
        }
    }
}

