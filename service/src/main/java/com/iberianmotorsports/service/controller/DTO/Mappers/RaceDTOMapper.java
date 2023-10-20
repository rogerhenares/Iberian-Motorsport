package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.RaceDTO;
import com.iberianmotorsports.service.controller.DTO.RaceRulesDTO;
import com.iberianmotorsports.service.controller.DTO.SessionDTO;
import com.iberianmotorsports.service.model.Race;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class RaceDTOMapper implements Function<Race, RaceDTO> {

    RaceRulesDTOMapper raceRulesDTOMapper;
    SessionDTOMapper sessionDTOMapper;
    BopDTOMapper bopDTOMapper;

    @Override
    public RaceDTO apply(Race race) {

        return new RaceDTO(
                race.getId(),
                race.getChampionship().getId(),
                race.getTrack(),
                race.getPreRaceWaitingTimeSeconds(),
                race.getSessionOverTimeSeconds(),
                race.getAmbientTemp(),
                race.getCloudLevel(),
                race.getRain(),
                race.getWeatherRandomness(),
                race.getPostQualySeconds(),
                race.getPostRaceSeconds(),
                race.getServerName(),
                race.getStartDate(),
                raceRulesDTOMapper.apply(race.getRaceRules()),
                race.getSessionList() != null ? race.getSessionList().stream().map(sessionDTOMapper).toList() : null,
                race.getBopList() != null ? race.getBopList().stream().map(bopDTOMapper).toList() : null
        );
    }

}
