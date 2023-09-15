package com.iberianmotorsports.service.model.parsing.export;

import lombok.Data;

@Data
public class EventRules {
    private Float qualifyStandingType;
    private Float pitWindowLengthSec;
    private Float driverStintTimeSec;
    private Float mandatoryPitstopCount;
    private Float maxTotalDrivingTime;
    private Float maxDriversCount;
    private Boolean isRefuellingAllowedInRace;
    private Boolean isRefuellingTimeFixed;
    private Boolean isMandatoryPitstopRefuellingRequired;
    private Boolean isMandatoryPitstopTyreChangeRequired;
    private Boolean isMandatoryPitstopSwapDriverRequired;
    private Float tyreSetCount;
}
