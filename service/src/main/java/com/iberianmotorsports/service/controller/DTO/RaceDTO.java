package com.iberianmotorsports.service.controller.DTO;

public record RaceDTO (
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
        Record raceRulesDTO,
        Record sessionDTO
) {
}
