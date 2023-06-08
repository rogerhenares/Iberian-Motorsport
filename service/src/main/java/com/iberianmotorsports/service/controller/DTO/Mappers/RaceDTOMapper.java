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
                raceRulesDTOMapper.apply(race.getRaceRules()),
                sessionDTOMapper.apply(race.getSession())

        );
    }

}
