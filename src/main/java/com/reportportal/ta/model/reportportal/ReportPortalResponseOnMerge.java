package com.reportportal.ta.model.reportportal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportPortalResponseOnMerge {

    String owner;
    Boolean share;
    Integer id;
    String uuid;
    String name;
    Integer number;
    Long startTime;
    Long endTime;
    Long lastModified;
    String status;
    List<String> statistics;
    List<String> attributes;
    String mode;
    List<String> analysing;
    Double approximateDuration;
    Boolean hasRetries;
    Boolean rerun;
}
