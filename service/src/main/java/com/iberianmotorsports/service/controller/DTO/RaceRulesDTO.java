package com.iberianmotorsports.service.controller.DTO;

public record RaceRulesDTO(
        Long qualifyStandingType,
        Integer pitWindowLengthSec,
        Integer driverStintTimeSec,
        Integer mandatoryPitstopCount,
        Integer maxTotalDrivingTime,
        Integer maxDriversCount,
        Integer isRefuellingAllowedInRace,
        Integer isRefuellingTimeFixed,
        Integer isMandatoryPitstopRefuellingRequired,
        Integer isMandatoryPitstopTyreChangeRequired,
        Integer isMandatoryPitstopSwapDriverRequired,
        Integer tyreSetCount
) {
}
