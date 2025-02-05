package com.reportportal.ta.model.reportportal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ReportPortalLogItem {

    private String itemUuid;

    private String launchUuid;

    private String message;

    private String level;

    private String time;

    private Map<String, String> file;

    /**
     * Create log item for the ReportPortal.
     *
     * @param itemUuid   Test item UUID
     * @param launchUuid Launch UUID
     * @param message    Log message
     * @param level      log level. Allowable values: 'error'('40000'), 'warn'('30000'), 'info'('20000'),
     *                   'debug'('10000'), 'trace'('5000'), 'fatal'('50000'), 'unknown'('60000')
     * @param date       Log time
     * @param file       It is needed only for file name and file content-type.
     *                   Notice that the file as it is not attaching here.
     */
    public ReportPortalLogItem(@NonNull String itemUuid, @NonNull String launchUuid,
                               @NonNull String message, @NonNull String level,
                               @NonNull Date date, @NonNull File file) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        this.setItemUuid(itemUuid);
        this.setLaunchUuid(launchUuid);
        this.setTime(dateFormat.format(date));
        this.setMessage(message);
        this.setLevel(level);
        try {
            this.setFile(Map.of("name", file.getName(), "contentType",
                Files.probeContentType(file.toPath())));
        } catch (IOException e) {
            log.error("Error while adding file to ReportPortal logItem", e);
        }
    }
}

