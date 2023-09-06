package com.iberianmotorsports.service.model.parsing;

import lombok.Data;

@Data
public class Settings {

    private String serverName;
    private String adminPassword;
    private String carGroup;
    private Float trackMedalsRequirement;
    private Float safetyRatingRequirement;
    private Float racecraftRatingRequirement;
    private String password;
    private String spectatorPassword;
    private Float maxCarSlots;
    private Float dumpLeaderboards;
    private Float isRaceLocked;
    private Float randomizeTrackWhenEmpty;
    private String centralEntryListPath;
    private Float allowAutoDq;
    private Float shortFormationLap;
    private Float dumpEntryList;
    private Float formationLapType;

}
