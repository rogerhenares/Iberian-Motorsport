package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.RaceRulesDTO;
import com.iberianmotorsports.service.controller.DTO.SessionDTO;
import com.iberianmotorsports.service.model.RaceRules;
import com.iberianmotorsports.service.model.Session;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class RaceRulesDTOMapper implements Function<RaceRules, RaceRulesDTO> {
    @Override
    public RaceRulesDTO apply(RaceRules raceRules) {
        if(raceRules == null) return null;
        return new RaceRulesDTO(
                raceRules.getId(),
                raceRules.getQualifyStandingType(),
                raceRules.getPitWindowLengthSec(),
                raceRules.getDriverStintTimeSec(),
                raceRules.getMandatoryPitstopCount(),
                raceRules.getMaxTotalDrivingTime(),
                raceRules.getMaxDriversCount(),
                raceRules.getIsRefuellingAllowedInRace(),
                raceRules.getIsRefuellingTimeFixed(),
                raceRules.getIsMandatoryPitstopRefuellingRequired(),
                raceRules.getIsMandatoryPitstopTyreChangeRequired(),
                raceRules.getIsMandatoryPitstopSwapDriverRequired(),
                raceRules.getTyreSetCount(),
                raceRules.getRace().getId()
        );
    }
}
