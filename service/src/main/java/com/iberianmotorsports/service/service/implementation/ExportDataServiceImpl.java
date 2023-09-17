package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.iberianmotorsports.service.model.*;
import com.iberianmotorsports.service.model.parsing.*;
import com.iberianmotorsports.service.model.parsing.export.*;
import com.iberianmotorsports.service.model.parsing.imports.Sessions;
import com.iberianmotorsports.service.model.parsing.properties.EntryListProperties;
import com.iberianmotorsports.service.model.parsing.properties.EntryProperties;
import com.iberianmotorsports.service.service.ExportDataService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Transactional
@Service("ExportDataService")
public class ExportDataServiceImpl implements ExportDataService {

    @Override
    public void exportData(Race race, EntryProperties entryProperties, EntryListProperties entryListProperties) throws Exception {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        mapper.writeValue(new File("settings.json"), getSettings(race.getChampionship(), race));
        mapper.writeValue(new File("event.json"), getEvent(race));
        mapper.writeValue(new File("eventRules.json"), getEventRules(race.getRaceRules()));

        List<Entry> entryList = new ArrayList<>();
        for (Grid grid : race.getChampionship().getGridList()) {
            if(!grid.getDisabled()){
                entryList.add(getEntry(generateDriverList(grid), entryProperties));
            }
        }
        mapper.writeValue(new File("entrylist.json"), getEntryList(entryList, entryListProperties));

    }

    public Settings getSettings(Championship championship, Race race) {
        Settings settings = new Settings();
        settings.setServerName("IML Iberian Motor Sports | " + championship.getName() + " | " + race.getTrack() + " | " + "#C" + championship.getId() + "R" + race.getId());
        settings.setAdminPassword(championship.getAdminPassword());
        settings.setCarGroup(championship.getCarGroup());
        settings.setTrackMedalsRequirement(championship.getTrackMedalsRequirement().floatValue());
        settings.setSafetyRatingRequirement(championship.getSafetyRatingRequirement().floatValue());
        settings.setRacecraftRatingRequirement(championship.getRacecraftRatingRequirement().floatValue());
        settings.setPassword(championship.getPassword());
        settings.setSpectatorPassword(championship.getSpectatorPassword());
        settings.setMaxCarSlots(championship.getMaxCarSlots().floatValue());
        settings.setDumpLeaderboards(championship.getDumpLeaderboards().floatValue());
        settings.setIsRaceLocked(championship.getIsRaceLocked().floatValue());
        settings.setRandomizeTrackWhenEmpty(championship.getRandomizeTrackWhenEmpty().floatValue());
        settings.setCentralEntryListPath(championship.getCentralEntryListPath());
        settings.setAllowAutoDq(championship.getAllowAutoDq().floatValue());
        settings.setShortFormationLap(championship.getFormationLapType().floatValue());
        settings.setDumpEntryList(championship.getDumpEntryList().floatValue());
        settings.setFormationLapType(championship.getFormationLapType().floatValue());
        return settings;
    }

    public Sessions getSessions(Session session) {
        Sessions sessions = new Sessions();
        sessions.setHourOfDay(session.getHourOfDay().floatValue());
        sessions.setDayOfWeekend(session.getDayOfWeekend().floatValue());
        sessions.setTimeMultiplier(session.getTimeMultiplier().floatValue());
        sessions.setSessionType(session.getSessionType());
        sessions.setSessionDurationMinutes(session.getSessionDurationMinutes().floatValue());
        return sessions;
    }

    public Event getEvent(Race race) {
        Event event = new Event();
        event.setTrack(race.getTrack());
        event.setPreRaceWaitingTimeSeconds(race.getPreRaceWaitingTimeSeconds().floatValue());
        event.setSessionOverTimeSeconds(race.getSessionOverTimeSeconds().floatValue());
        event.setAmbientTemp(race.getAmbientTemp().floatValue());
        event.setCloudLevel(race.getCloudLevel());
        event.setRain(race.getRain());
        event.setWeatherRandomness(race.getWeatherRandomness().floatValue());
        event.setSessions(race.getSessionList().stream().map(this::getSessions).toList());
        return event;
    }

    public EventRules getEventRules(RaceRules raceRules) {
        EventRules eventRules = new EventRules();
        eventRules.setQualifyStandingType(raceRules.getQualifyStandingType().floatValue());
        eventRules.setPitWindowLengthSec(raceRules.getPitWindowLengthSec().floatValue());
        eventRules.setDriverStintTimeSec(raceRules.getDriverStintTimeSec().floatValue());
        eventRules.setMandatoryPitstopCount(raceRules.getMandatoryPitstopCount().floatValue());
        eventRules.setMaxTotalDrivingTime(raceRules.getMaxTotalDrivingTime().floatValue());
        eventRules.setMaxDriversCount(raceRules.getMaxDriversCount().floatValue());
        eventRules.setIsRefuellingAllowedInRace(raceRules.getIsRefuellingAllowedInRace() == 1);
        eventRules.setIsRefuellingTimeFixed(raceRules.getIsRefuellingTimeFixed() == 1);
        eventRules.setIsMandatoryPitstopRefuellingRequired(raceRules.getIsMandatoryPitstopRefuellingRequired() == 1);
        eventRules.setIsMandatoryPitstopTyreChangeRequired(raceRules.getIsMandatoryPitstopTyreChangeRequired() == 1);
        eventRules.setIsMandatoryPitstopSwapDriverRequired(raceRules.getIsMandatoryPitstopSwapDriverRequired() == 1);
        eventRules.setTyreSetCount(raceRules.getTyreSetCount().floatValue());
        return eventRules;
    }

    public Driver getDriver(User user) {
        Driver driver = new Driver();
        driver.setFirstName(user.getFirstName());
        driver.setLastName(user.getLastName());
        driver.setShortName(user.getShortName());
        driver.setDriverCategory(1F);
        driver.setPlayerID("S" + user.getSteamId());
        return driver;
    }

    public List<Driver> generateDriverList(Grid grid) {
        List<Driver> driverList = new ArrayList<>();
        for (User user : grid.getDrivers()) {
            driverList.add(getDriver(user));
        }
        return driverList;
    }


    public Entry getEntry(List<Driver> driverList, EntryProperties entryProperties) {
        Entry entry = new Entry();
        entry.setDrivers(driverList);
        entry.setOverrideDriverInfo(entryProperties.getOverrideDriverInfo());
        entry.setIsServerAdmin(entryProperties.getIsServerAdmin());
        return entry;
    }


    public EntryList getEntryList(List<Entry> entries, EntryListProperties entryListProperties) {
        EntryList entryList = new EntryList();
        entryList.setEntries(entries);
        entryList.setForceEntryList(entryListProperties.getForceEntryList());
        return entryList;
    }

}
