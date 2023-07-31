package com.iberianmotorsports.service.controller.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record RaceDTO (
        Long id,
        Long championshipId,
        String track,
        Integer preRaceWaitingTimeSeconds,
        Integer sessionOverTimeSeconds,
        Integer ambientTemp,
        Float cloudLevel,
        Float rain,
        Integer weatherRandomness,
        Integer postQualySeconds,
        Integer postRaceSeconds,
        String serverName,
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime startDate,
        RaceRulesDTO raceRulesDTO,
        List<SessionDTO> sessionDTOList
) {
}
