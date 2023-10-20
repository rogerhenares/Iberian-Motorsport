package com.iberianmotorsports.service.controller.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record ChampionshipDTO(
        Long id,
        String name,
        String description,
        String adminPassword,
        String carGroup,
        String subCarGroup,
        Integer trackMedalsRequirement,
        Integer safetyRatingRequirement,
        Integer racecraftRatingRequirement,
        String password,
        String spectatorPassword,
        Integer maxCarSlots,
        Integer maxSubCarSlots,
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
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime startDate,
        String style,
        List<RaceDTO> raceList,
        Boolean disabled,
        Boolean started,
        Boolean finished,
        List<ChampionshipCategoryDTO> championshipCategoryList,
        List<CarDTO> carList,
        Boolean isLoggedUserInChampionship
) {
}
