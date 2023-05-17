package com.iberianmotorsports;

import com.iberianmotorsports.service.model.Championship;

public class ChampionshipFactory {

    public static final Long id = 1L;
    public static final String name = "testName";
    public static final String description = "testDescription";
    public static final String adminPassword = "testAdminPassword";
    public static final String carGroup = "testCarGroup";
    public static final Integer trackMedalsRequirement = 0;
    public static final Integer safetyRatingRequirement = 0;
    public static final Integer racecraftRatingRequirement = 0;
    public static final String password = "testPassword";
    public static final String spectatorPassword = "testSpectatorPassword";
    public static final Integer maxCarSlots = 0;
    public static final Integer dumpLeaderboards = 1;
    public static final Integer isRaceLocked = 0;

    public static Championship championship() {
        Championship championship = new Championship();
        championship.setId(id);
        championship.setName(name);
        championship.setDescription(description);
        championship.setPassword(password);
        championship.setCarGroup(carGroup);
        championship.setAdmin_password(adminPassword);
        championship.setDumpLeaderboards(dumpLeaderboards);
        championship.setIsRaceLocked(isRaceLocked);
        championship.setMaxCarSlots(maxCarSlots);
        championship.setSpectatorPassword(spectatorPassword);
        championship.setTrackMedalsRequirement(trackMedalsRequirement);
        championship.setSafetyRatingRequirement(safetyRatingRequirement);
        championship.setRacecraftRatingRequirement(racecraftRatingRequirement);
        return championship;
    }

    public static Championship championshipInvalidFormat() {
        Championship championship = new Championship();
        championship.setId(id);
        championship.setName(null);
        return championship;
    }


}