package com.reportportal.ta.services.reportportal;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public abstract class ReportPortalService {

    @Value("${rp.endpoint}")
    protected String reportPortalBaseUrl;

    @Value("${rp.uuid}")
    protected String uuid;

    @Value("${rp.project}")
    protected String projectName;

    @Value("${rp.launch}")
    protected String launchName;

    protected WebClient webClient;

    protected static final String ERR_MSG = "Error response code is: %s and message is %s";
    protected static final String LOG_ERR_MSG = "Error response code is: {} and message is {}";
    protected static final String RESPONSE_EXC_RETRIEVE = "WebClientResponseException in retrieve process";

    @PostConstruct
    private void initWebClient() {
        webClient = WebClient
            .builder()
            .baseUrl(reportPortalBaseUrl + "/api/v1/" + projectName)
            .defaultHeader("authorization", "bearer " + uuid)
            .filter(logRequest())
            .build();
    }

    // This method returns filter function which will log request data
    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }
}