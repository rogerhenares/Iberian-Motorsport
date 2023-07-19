package com.iberianmotorsports.service.controller.DTO;

import com.iberianmotorsports.service.model.Race;

public record SessionDTO(
        Long id,
        Integer hourOfDay,
        Integer dayOfWeekend,
        Integer timeMultiplier,
        String sessionType,
        Integer sessionDurationMinutes,
        Long raceId
) {
}