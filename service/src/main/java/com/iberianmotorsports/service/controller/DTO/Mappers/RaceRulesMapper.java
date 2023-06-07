package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.RaceRulesDTO;
import com.iberianmotorsports.service.model.RaceRules;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RaceRulesMapper implements Function<RaceRulesDTO, RaceRules> {
    @Override
    public RaceRules apply(RaceRulesDTO raceRulesDTO) {
        RaceRules raceRules = new RaceRules();
        raceRules.setQualifyStandingType(raceRulesDTO.qualifyStandingType());
        raceRules.setPitWindowLengthSec(raceRulesDTO.pitWindowLengthSec());
        raceRules.setDriverStintTimeSec(raceRulesDTO.driverStintTimeSec());
        raceRules.setMandatoryPitstopCount(raceRulesDTO.mandatoryPitstopCount());
        raceRules.setMaxTotalDrivingTime(raceRulesDTO.maxTotalDrivingTime());
        raceRules.setMaxDriversCount(raceRulesDTO.maxDriversCount());
        raceRules.setIsRefuellingAllowedInRace(raceRulesDTO.isRefuellingAllowedInRace());
        raceRules.setIsRefuellingTimeFixed(raceRulesDTO.isRefuellingTimeFixed());
        raceRules.setIsMandatoryPitstopRefuellingRequired(raceRulesDTO.isMandatoryPitstopRefuellingRequired());
        raceRules.setIsMandatoryPitstopTyreChangeRequired(raceRulesDTO.isMandatoryPitstopTyreChangeRequired());
        raceRules.setIsMandatoryPitstopSwapDriverRequired(raceRulesDTO.isMandatoryPitstopSwapDriverRequired());
        raceRules.setTyreSetCount(raceRulesDTO.tyreSetCount());
        return raceRules;
    }
}
