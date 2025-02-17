package com.reportportal.ta.services.reportportal;

import com.google.gson.Gson;
import com.reportportal.ta.exception.WebClientConfigurationException;
import com.reportportal.ta.model.reportportal.ReportPortalLogItem;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Lazy
@Slf4j
public class ReportPortalLogService extends ReportPortalService {

    public String postLogFile(ReportPortalLogItem logItem, File attachment) {

        String json = new Gson().toJson(new ArrayList<>(
            Collections.singletonList(logItem)
        ));

        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("json_request_part", json).contentType(MediaType.APPLICATION_JSON);
        bodyBuilder.part("file", new FileSystemResource(attachment));

        try {
            ClientResponse clientResponse = webClient
                .post()
                .uri("/log")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(bodyBuilder.build())
                .exchange()
                .block();

            String response = clientResponse.toEntity(String.class).block().toString();
            log.info("Response: {}", new Gson().toJson(response));

            return clientResponse.statusCode().toString();
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);

            throw e;
        } catch (WebClientResponseException e) {
            log.error(LOG_ERR_MSG, e.getRawStatusCode(), e.getResponseBodyAsString(), e);

            throw new WebClientConfigurationException(
                String.format(ERR_MSG, e.getRawStatusCode(), e.getResponseBodyAsString()), e);
        }
    }
}
