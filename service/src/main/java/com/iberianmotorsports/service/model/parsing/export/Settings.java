package com.iberianmotorsports.service.model.parsing.export;

import lombok.Data;

@Data
public class Settings {

    private String serverName;
    private String adminPassword;
    private String carGroup;
    private Integer trackMedalsRequirement;
    private Integer safetyRatingRequirement;
    private Integer racecraftRatingRequirement;
    private String password;
    private String spectatorPassword;
    private Integer maxCarSlots;
    private Integer dumpLeaderboards;
    private Integer isRaceLocked;
    private Integer randomizeTrackWhenEmpty;
    private String centralEntryListPath;
    private Integer allowAutoDq;
    private Integer shortFormationLap;
    private Integer dumpEntryList;
    private Integer formationLapType;

}
