package com.iberianmotorsports.service.model.parsing.imports;

import lombok.Data;

@Data
public class Sessions {
    private Integer hourOfDay;
    private Integer dayOfWeekend;
    private Integer timeMultiplier;
    private String sessionType;
    private Integer sessionDurationMinutes;
}
