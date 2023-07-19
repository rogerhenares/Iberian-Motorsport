package com.iberianmotorsports.service.controller.DTO;

import com.iberianmotorsports.service.model.Race;

import java.time.LocalDateTime;
import java.util.List;

public record ChampionshipDTO(
        Long id,
        String name,
        String description,
        String adminPassword,
        String carGroup,
        Integer trackMedalsRequirement,
        Integer safetyRatingRequirement,
        Integer racecraftRatingRequirement,
        String password,
        String spectatorPassword,
        Integer maxCarSlots,
        Integer dumpLeaderboards,
        Integer isRaceLocked,
        Integer randomizeTrackWhenEmpty,
        String centralEntryListPath,
        Integer allowAutoDq,
        Integer shortFormationLap,
        Integer dumpEntryList,
        Integer formationLapType,
        Integer ignorePrematureDisconnects,
        String imageContent,
        LocalDateTime startDate,
        List<RaceDTO> raceList
) {
}
