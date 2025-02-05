package com.reportportal.ta.services.reportportal;

import com.reportportal.ta.exception.WebClientConfigurationException;
import com.reportportal.ta.model.ReportPortalResponse;
import com.reportportal.ta.model.reportportal.ReportPortalTestItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Lazy
@Slf4j
public class ReportPortalTestItemService extends ReportPortalService {

    @Autowired
    private ReportPortalLaunchService reportPortalLaunchService;

    /**
     * Get current step uuid from Report Portal.
     *
     * @return Current step UUID. Step must have status IN_PROGRESS.
     */
    public String getCurrentStepTestItemUuid() {
        int launchItemId = reportPortalLaunchService.getCurrentLaunchId();

        if (launchItemId == 0) {
            return null;
        }

        ReportPortalResponse<ReportPortalTestItem> response;
        try {
            response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                    .path("/item")
                    .queryParam("filter.eq.launchId", launchItemId)
                    .queryParam("filter.eq.status", "IN_PROGRESS")
                    .queryParam("page.sort", "startTime,DESC")
                    .queryParam("page.size", "1")
                    .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ReportPortalResponse<ReportPortalTestItem>>() {
                })
                .block();
        } catch (WebClientResponseException e) {
            log.error(LOG_ERR_MSG, e.getRawStatusCode(), e.getResponseBodyAsString());
            log.error(RESPONSE_EXC_RETRIEVE, e);
            throw new WebClientConfigurationException(
                String.format(ERR_MSG, e.getRawStatusCode(), e.getResponseBodyAsString()), e);
        }

        if (response == null || response.getContent().length == 0) {
            return null;
        }

        return response.getContent()[0].getUuid();
    }
}
