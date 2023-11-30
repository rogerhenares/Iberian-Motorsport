package com.iberianmotorsports.service.model.parsing.imports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sessions {
    private Integer hourOfDay;
    private Integer dayOfWeekend;
    private Integer timeMultiplier;
    private String sessionType;
    private Integer sessionDurationMinutes;
}
