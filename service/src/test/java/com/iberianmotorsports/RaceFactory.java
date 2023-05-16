package com.iberianmotorsports;

import com.iberianmotorsports.service.model.Race;

public class RaceFactory {
    public static final Long id = 1L;
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
        return race;
    }
}
