package com.iberianmotorsports.service.controller.DTO;

public record SessionDTO(
        Long id,
        Integer hourOfDay,
        Integer dayOfWeekend,
        Integer timeMultiplier,
        String sessionType,
        Integer sessionDurationMinutes
) {
}
