package com.iberianmotorsports.service.model.parsing;

import lombok.Data;

@Data
public class Sessions {
    private Float hourOfDay;
    private Float dayOfWeekend;
    private Float timeMultiplier;
    private String sessionType;
    private Float sessionDurationMinutes;
}
