package com.reportportal.ta.services.reportportal;

import com.reportportal.ta.exception.WebClientConfigurationException;
import com.reportportal.ta.model.ReportPortalLaunchItem;
import com.reportportal.ta.model.ReportPortalResponse;
import com.reportportal.ta.model.reportportal.MergeLaunchesRequestBody;
import com.reportportal.ta.model.reportportal.ReportPortalResponseOnMerge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ReportPortalLaunchService extends ReportPortalService {

    private static final String MERGED_REPORT_NAME = "EPM-LSTR-LAMA";
    private static final String MERGED_REPORT_DESCRIPTION = "Merged report description";
    private static final String MODE = "DEFAULT";
    private static final String MERGE_TYPE_DEEP = "DEEP";

    /**
     * Get current launch id from Report Portal.
     *
     * @return Current launch ID. Launch must have status IN_PROGRESS.
     */

    public int getCurrentLaunchId() {
        return getCurrentLaunch() == null ? 0 : getCurrentLaunch().getId();
    }

    /**
     * Get current launch uuid from Report Portal.
     *
     * @return Current launch UUID. Launch must have status IN_PROGRESS.
     */
    public String getCurrentLaunchUuid() {
        return getCurrentLaunch() == null ? null : getCurrentLaunch().getUuid();
    }

    private ReportPortalLaunchItem getCurrentLaunch() {
        ReportPortalResponse<ReportPortalLaunchItem> response;
        try {
            response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                    .path("/launch")
                    .queryParam("filter.cnt.name", launchName)
                    .queryParam("filter.eq.status", "IN_PROGRESS")
                    .queryParam("page.sort", "startTime,number,DESC")
                    .queryParam("page.size", "1")
                    .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ReportPortalResponse<ReportPortalLaunchItem>>() {
                })
                .log()
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
        return response.getContent()[0];
    }

    /**
     * Get specified by parameter number of launches from ReportPortal.
     *
     * @param numberOfLaunches amount of launches need to return.
     *
     * @return specified amount of launches. Last numberOfLaunches launches from RP.
     */
    public List<ReportPortalLaunchItem> getLaunches(int numberOfLaunches) {
        ReportPortalResponse<ReportPortalLaunchItem> response;
        try {
            response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                    .path("/launch")
                    .queryParam("page.sort", "startTime,number,DESC")
                    .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ReportPortalResponse<ReportPortalLaunchItem>>() {
                })
                .log()
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
        return Arrays.stream(response.getContent())
                     .limit(numberOfLaunches)
                     .collect(Collectors.toList());
    }

    /**
     * Merge specified by parameter number of reports(launches) in ReportPortal.
     *
     * @param numberOfLaunches - number of reports(launches) that need to be merged into a single report(launches).
     */
    public void mergeLaunches(int numberOfLaunches) {
        MergeLaunchesRequestBody body = new MergeLaunchesRequestBody();
        List<Integer> launchesID = new ArrayList<>();
        List<ReportPortalLaunchItem> launches = getLaunches(numberOfLaunches);
        for (ReportPortalLaunchItem launch : launches) {
            launchesID.add(launch.getId());
        }
        body.setEndTime(null);
        body.setAttributes(null);
        body.setLaunches(launchesID);
        body.setMergeType(MERGE_TYPE_DEEP);
        body.setMode(MODE);
        body.setName(MERGED_REPORT_NAME);
        body.setStartTime(null);
        body.setDescription(MERGED_REPORT_DESCRIPTION);
        body.setExtendSuitesDescription(true);

        try {
            webClient
                .method(HttpMethod.POST)
                .uri("/launch/merge")
                .body(Mono.just(body), MergeLaunchesRequestBody.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ReportPortalResponse<ReportPortalResponseOnMerge>>() {
                })
                .log()
                .block();
        } catch (WebClientResponseException e) {
            log.error(LOG_ERR_MSG, e.getRawStatusCode(), e.getResponseBodyAsString());
            log.error(RESPONSE_EXC_RETRIEVE, e);
            throw new WebClientConfigurationException(
                String.format(ERR_MSG, e.getRawStatusCode(), e.getResponseBodyAsString()), e);
        }
        log.info("Merged " + numberOfLaunches + " reports of launches.");
    }
}