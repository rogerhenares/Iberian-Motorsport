package com.iberianmotorsports;

import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.Race;

import java.util.ArrayList;
import java.util.List;

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
    public static final Integer randomizeTrackWhenEmpty = 0;
    public static final String centralEntryListPath = "testCentralEntryListPath";
    public static final Integer allowAutoDq = 0;
    public static final Integer shortFormationLap = 0;
    public static final Integer dumpEntryList = 0;
    public static final Integer formationLapType = 0;
    public static final Integer ignorePrematureDisconnects = 0;
    public static final String imageContent = "testImage";


    public static Championship championship() {
        Championship championship = new Championship();
        championship.setId(id);
        championship.setName(name);
        championship.setDescription(description);
        championship.setPassword(password);
        championship.setCarGroup(carGroup);
        championship.setAdminPassword(adminPassword);
        championship.setDumpLeaderboards(dumpLeaderboards);
        championship.setIsRaceLocked(isRaceLocked);
        championship.setMaxCarSlots(maxCarSlots);
        championship.setSpectatorPassword(spectatorPassword);
        championship.setTrackMedalsRequirement(trackMedalsRequirement);
        championship.setSafetyRatingRequirement(safetyRatingRequirement);
        championship.setRacecraftRatingRequirement(racecraftRatingRequirement);
        championship.setRandomizeTrackWhenEmpty(randomizeTrackWhenEmpty);
        championship.setCentralEntryListPath(centralEntryListPath);
        championship.setAllowAutoDq(allowAutoDq);
        championship.setShortFormationLap(shortFormationLap);
        championship.setDumpEntryList(dumpEntryList);
        championship.setFormationLapType(formationLapType);
        championship.setIgnorePrematureDisconnects(ignorePrematureDisconnects);
        championship.setImageContent(imageContent);
        return championship;
    }

    public static Championship championshipInvalidFormat() {
        Championship championship = new Championship();
        championship.setId(id);
        championship.setName(null);
        return championship;
    }


}
