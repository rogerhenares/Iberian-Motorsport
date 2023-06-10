package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.RaceDTO;
import com.iberianmotorsports.service.controller.DTO.RaceRulesDTO;
import com.iberianmotorsports.service.controller.DTO.SessionDTO;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.repository.ChampionshipRepository;
import com.iberianmotorsports.service.service.ChampionshipService;
import com.iberianmotorsports.service.service.RaceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class RaceMapper implements Function<RaceDTO, Race> {

    RaceRulesMapper raceRulesMapper;
    SessionMapper sessionMapper;
    private final ChampionshipRepository championshipRepository;

    @Override
    public Race apply(RaceDTO raceDTO) {
        Race race = new Race();
        race.setId(raceDTO.id());
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
        race.setChampionshipId(raceDTO.championshipId());
        race.setChampionship(championshipRepository.findById(raceDTO.championshipId()).get());
        race.setRaceRules(raceRulesMapper.apply((RaceRulesDTO) raceDTO.raceRulesDTO()));
        race.setSession(sessionMapper.apply((SessionDTO) raceDTO.sessionDTO()));
        return race;
    }
}