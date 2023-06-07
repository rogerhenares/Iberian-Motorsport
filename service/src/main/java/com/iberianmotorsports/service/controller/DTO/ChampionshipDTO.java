package com.iberianmotorsports.service.controller.DTO;

public record ChampionshipDTO(
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
        String imageContent
) {
}
