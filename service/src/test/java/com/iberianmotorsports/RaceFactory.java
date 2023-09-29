package com.iberianmotorsports;

import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.RaceRules;
import com.iberianmotorsports.service.model.Session;

import java.util.ArrayList;
import java.util.List;

public class RaceFactory {
    public static final Long id = 368L;
    public static final String track = "testTrack";
    public static final Integer preRaceWaitingTimeSeconds = 30;
    public static final Integer sessionOverTimeSeconds = 0;
    public static final Integer ambientTemp = 0;
    public static final Float cloudLevel = 0F;
    public static final Float rain = 0F;
    public static final Integer weatherRandomness = 0;
    public static final Integer postQualySeconds = 1;
    public static final Integer postRaceSeconds = 0;
    public static final String serverName = "testServerName";
    public static final Long championshipId = 1L;
    public static final Championship championship = new Championship();
    public static final RaceRules raceRules = new RaceRules();
    public static final List<Session> sessionList = new ArrayList<>();


    public static Race race() {
        Race race = new Race();
        race.setId(id);
        race.setTrack(track);
        race.setPostRaceSeconds(postRaceSeconds);
        race.setRain(rain);
        race.setAmbientTemp(ambientTemp);
        race.setCloudLevel(cloudLevel);
        race.setPreRaceWaitingTimeSeconds(preRaceWaitingTimeSeconds);
        race.setServerName(serverName);
        race.setPostQualySeconds(postQualySeconds);
        race.setSessionOverTimeSeconds(sessionOverTimeSeconds);
        race.setWeatherRandomness(weatherRandomness);
        race.setChampionshipId(championshipId);
        race.setChampionship(ChampionshipFactory.championship());
        race.setRaceRules(RaceRulesFactory.raceRules());
        race.setSessionList(SessionFactory.sessionList());
        return race;
    }
}
