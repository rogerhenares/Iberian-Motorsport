package com.iberianmotorsports;

import com.iberianmotorsports.service.model.RaceRules;

public class RaceRulesFactory {
    public static final Long id = 1L;
    public static final Long qualifyStandingType = 1L;
    public static final Integer pitWindowLengthSec = 0;
    public static final Integer driverStintTimeSec = 0;
    public static final Integer mandatoryPitstopCount = 0;
    public static final Integer maxTotalDrivingTime = 0;
    public static final Integer maxDriversCount = 1;
    public static final Integer isRefuellingAllowedInRace = 0;
    public static final Integer isRefuellingTimeFixed = 0;
    public static final Integer isMandatoryPitstopRefuellingRequired = 0;
    public static final Integer isMandatoryPitstopTyreChangeRequired = 0;
    public static final Integer isMandatoryPitstopSwapDriverRequired = 0;
    public static final Integer tyreSetCount = 0;

    public static RaceRules raceRules() {
        RaceRules raceRules = new RaceRules();
        raceRules.setId(id);
        raceRules.setIsRefuellingAllowedInRace(isRefuellingAllowedInRace);
        raceRules.setDriverStintTimeSec(driverStintTimeSec);
        raceRules.setIsMandatoryPitstopTyreChangeRequired(isMandatoryPitstopTyreChangeRequired);
        raceRules.setIsRefuellingTimeFixed(isRefuellingTimeFixed);
        raceRules.setIsMandatoryPitstopRefuellingRequired(isMandatoryPitstopRefuellingRequired);
        raceRules.setIsMandatoryPitstopSwapDriverRequired(isMandatoryPitstopSwapDriverRequired);
        raceRules.setIsRefuellingAllowedInRace(isRefuellingAllowedInRace);
        raceRules.setMandatoryPitstopCount(mandatoryPitstopCount);
        raceRules.setMaxDriversCount(maxDriversCount);
        raceRules.setMaxTotalDrivingTime(maxTotalDrivingTime);
        raceRules.setPitWindowLengthSec(pitWindowLengthSec);
        raceRules.setQualifyStandingType(qualifyStandingType);
        raceRules.setTyreSetCount(tyreSetCount);
        return raceRules;
    }
}
