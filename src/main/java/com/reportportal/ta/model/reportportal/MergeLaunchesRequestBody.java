package com.reportportal.ta.model.reportportal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MergeLaunchesRequestBody {

    private List<String> attributes;
    private String description;
    private Long endTime;
    private Boolean extendSuitesDescription;
    private List<Integer> launches;
    private String mergeType;
    private String mode;
    private String name;
    private Long startTime;
}