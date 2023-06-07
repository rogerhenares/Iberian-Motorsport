package com.iberianmotorsports.service.controller.DTO;

public record SessionDTO(
        Integer hourOfDay,
        Integer dayOfWeekend,
        Integer timeMultiplier,
        String sessionType,
        Integer sessionDurationMinutes
) {
}
