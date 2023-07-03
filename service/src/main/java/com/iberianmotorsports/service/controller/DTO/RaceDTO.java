package com.iberianmotorsports.service.controller.DTO;

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
        RaceRulesDTO raceRulesDTO,
        List<SessionDTO> sessionDTOList
) {
}
