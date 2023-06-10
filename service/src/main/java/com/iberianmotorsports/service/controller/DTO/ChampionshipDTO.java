package com.iberianmotorsports.service.controller.DTO;

import java.time.LocalDateTime;

public record ChampionshipDTO(
        Long id,
        String name,
        String description,
        String admin_password,
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
        LocalDateTime startDate
) {
}
