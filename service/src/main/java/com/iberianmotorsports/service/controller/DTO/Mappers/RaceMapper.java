package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.RaceDTO;
import com.iberianmotorsports.service.controller.DTO.RaceRulesDTO;
import com.iberianmotorsports.service.controller.DTO.SessionDTO;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.composeKey.BopPrimaryKey;
import com.iberianmotorsports.service.repository.ChampionshipRepository;
import com.iberianmotorsports.service.service.ChampionshipService;
import com.iberianmotorsports.service.service.RaceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class RaceMapper implements Function<RaceDTO, Race> {

    @Autowired
    RaceRulesMapper raceRulesMapper;
    @Autowired
    SessionMapper sessionMapper;
    @Autowired
    BopMapper bopMapper;
    @Autowired
    CarMapper carMapper;

    @Override
    public Race apply(RaceDTO raceDTO) {
        Race race = new Race();
        race.setId(raceDTO.id());
        race.setChampionshipId(raceDTO.championshipId());
        race.setTrack(raceDTO.track());
        race.setPreRaceWaitingTimeSeconds(raceDTO.preRaceWaitingTimeSeconds());
        race.setSessionOverTimeSeconds(raceDTO.sessionOverTimeSeconds());
        race.setAmbientTemp(raceDTO.ambientTemp());
        race.setCloudLevel(raceDTO.cloudLevel());
        race.setRain(raceDTO.rain());
        race.setWeatherRandomness(raceDTO.weatherRandomness());
        race.setPostQualySeconds(raceDTO.postQualySeconds());
        race.setPostRaceSeconds(raceDTO.postRaceSeconds());
        race.setServerName(raceDTO.serverName());
        race.setStartDate(raceDTO.startDate());
        race.setRaceRules(raceRulesMapper.apply(raceDTO.raceRulesDTO()));
        race.getRaceRules().setRace(race);
        if(raceDTO.sessionDTOList() != null) race.setSessionList(raceDTO.sessionDTOList().stream()
                .map(sessionMapper)
                .map(session -> {
                    session.setRace(race);
                    return session;
                })
                .toList());
        if(raceDTO.bopDTOList() != null) race.setBopList(raceDTO.bopDTOList().stream()
                .map(bopMapper)
                .map(bop -> {
                    bop.getBopPrimaryKey().setRace(race);
                    return bop;
                })
                .toList());
        return race;
    }
}
