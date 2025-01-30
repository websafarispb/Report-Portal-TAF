package com.reportportal.ta.configuration;

import com.reportportal.ta.services.api.ReportPortalDashboardService;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class RestServiceConfiguration {

    private static final String BASE_URL = "http://localhost:8080/api/v1";
    private static final String PROJECT_NAME = "default_personal";
    private static final String TOKEN = "Bearer New-one_iBSWshtlQfqT-UXZEl6Y-0f0Vo3wEIC6AcGLDxr3mxBRmPsU-br73pWlzWCAXwLD";


    public RequestSpecification getRequestSpecification() {
        return RestAssured.given()
                          .baseUri(BASE_URL)
                          .header("Authorization", TOKEN);
    }

    @Bean
    @Lazy
    public ReportPortalDashboardService reportPortalDashboardService() {
        return new ReportPortalDashboardService(getRequestSpecification());
    }
}
