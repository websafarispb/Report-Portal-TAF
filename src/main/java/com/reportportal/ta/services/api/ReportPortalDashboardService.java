package com.reportportal.ta.services.api;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportPortalDashboardService {

    private static final String PROJECT_NAME = "default_personal";
    private static final String TOKEN = "Bearer New-one_iBSWshtlQfqT-UXZEl6Y-0f0Vo3wEIC6AcGLDxr3mxBRmPsU-br73pWlzWCAXwLD";

    protected final RequestSpecification requestSpecification;

    public ReportPortalDashboardService(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public Response deleteDashboard(final String dashboardId) {
        log.info("deleteDashboard");
        return given()
            .spec(requestSpecification)
            .basePath("/{project}/dashboard/{id}")
            .pathParam("project", PROJECT_NAME)
            .pathParam("id", dashboardId)
            .header("Authorization", TOKEN)
            .delete();
    }
}
