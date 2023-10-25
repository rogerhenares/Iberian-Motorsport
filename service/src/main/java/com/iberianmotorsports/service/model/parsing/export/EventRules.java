package com.iberianmotorsports.service.model.parsing.export;

import lombok.Data;

@Data
public class EventRules {
    private Long qualifyStandingType;
    private Integer pitWindowLengthSec;
    private Integer driverStintTimeSec;
    private Integer mandatoryPitstopCount;
    private Integer maxTotalDrivingTime;
    private Integer maxDriversCount;
    private Boolean isRefuellingAllowedInRace;
    private Boolean isRefuellingTimeFixed;
    private Boolean isMandatoryPitstopRefuellingRequired;
    private Boolean isMandatoryPitstopTyreChangeRequired;
    private Boolean isMandatoryPitstopSwapDriverRequired;
    private Integer tyreSetCount;
}
