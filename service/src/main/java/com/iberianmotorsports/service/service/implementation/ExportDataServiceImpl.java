package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.iberianmotorsports.service.model.*;
import com.iberianmotorsports.service.model.parsing.Driver;
import com.iberianmotorsports.service.model.parsing.export.*;
import com.iberianmotorsports.service.model.parsing.imports.Sessions;
import com.iberianmotorsports.service.model.parsing.properties.EntryListProperties;
import com.iberianmotorsports.service.model.parsing.properties.EntryProperties;
import com.iberianmotorsports.service.model.parsing.properties.ServerProperty;
import com.iberianmotorsports.service.service.ExportDataService;
import com.iberianmotorsports.service.service.RaceService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.aspectj.util.FileUtil;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Transactional
@Service("ExportDataService")
public class ExportDataServiceImpl implements ExportDataService {

    private static final String FILE_NAME_BOP = "bop.json";
    private static final String FILE_NAME_SETTINGS = "settings.json";
    private static final String FILE_NAME_EVENT = "event.json";
    private static final String FILE_NAME_EVENT_RULES = "eventRules.json";
    private static final String FILE_NAME_ENTRYLIST = "entrylist.json";
    public static final String SERVER_NAME_SEPARATOR = " | ";
    public static final String SERVER_FOLDER_SEPARATOR = "-";
    private static final String CFG_FOLDER_NAME = "cfg";

    private final RaceService raceService;
    private final ServerProperty serverProperty;
    private final EntryProperties entryProperties;
    private final EntryListProperties entryListProperties;

    @Override
    public void exportData(Race race) throws Exception {
        if (Objects.isNull(race.getChampionship())){
            race = raceService.findRaceById(race.getId());
        }
        File championshipFolder = getChampionshipFolder(race);
        createFolderIfNotExist(championshipFolder);
        File raceFolder = getRaceFolder(race);
        createFolderIfNotExist(raceFolder);
        File defaultFilesFolder = new File(serverProperty.getDefaultFilesFolder());
        if (defaultFilesFolder.exists()){
            FileUtil.copyDir(defaultFilesFolder, raceFolder);
        }
        File cfgFolder = new File(raceFolder.getAbsolutePath() + File.separator + CFG_FOLDER_NAME);
        createFolderIfNotExist(raceFolder);

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        mapper.writeValue(new File(cfgFolder.getAbsolutePath() + File.separator + FILE_NAME_BOP), getBopEntries(race.getBopList(), race.getTrack()));
        mapper.writeValue(new File(cfgFolder.getAbsolutePath() + File.separator + FILE_NAME_SETTINGS), getSettings(race.getChampionship(), race));
        mapper.writeValue(new File(cfgFolder.getAbsolutePath() + File.separator + FILE_NAME_EVENT), getEvent(race));
        mapper.writeValue(new File(cfgFolder.getAbsolutePath() + File.separator + FILE_NAME_EVENT_RULES), getEventRules(race.getRaceRules()));

        List<Entry> entryList = new ArrayList<>();
        for (Grid grid : race.getChampionship().getGridList()) {
            if(!grid.getDisabled()){
                entryList.add(getEntry(generateDriverList(grid), entryProperties, grid));
            }
        }
        mapper.writeValue(new File(cfgFolder.getAbsolutePath() + File.separator + FILE_NAME_ENTRYLIST), getEntryList(entryList, entryListProperties));
    }

    @Override
    public File getAccServerDir(Race race) {
        return getRaceFolder(race);
    }

    private File getChampionshipFolder(Race race) {
        return new File(serverProperty.getFolder() + File.separator + "C" + race.getChampionship().getId() +
                SERVER_FOLDER_SEPARATOR + race.getChampionship().getName());
    }

    private File getRaceFolder(Race race) {
        return new File(getChampionshipFolder(race).getAbsolutePath() + File.separator + "R" + race.getId() +
                SERVER_FOLDER_SEPARATOR + race.getTrack());
    }

    private void createFolderIfNotExist(File folder) {
        if(!folder.exists()){
            Boolean createdFolder = folder.mkdir();
            if(!createdFolder) {
                throw new ServiceException(String.format("Unable to create folder for path -> {}", folder.getAbsolutePath()));
            }
        }
    }

    public Settings getSettings(Championship championship, Race race) {
        Settings settings = new Settings();
        settings.setServerName(serverProperty.getServerName() + SERVER_NAME_SEPARATOR +
                championship.getName() + SERVER_NAME_SEPARATOR +
                race.getTrack() + SERVER_NAME_SEPARATOR +
                findRaceRound(championship, race) + SERVER_NAME_SEPARATOR +
                "#C" + championship.getId() + "R" + race.getId());
        settings.setAdminPassword(championship.getAdminPassword());
        settings.setCarGroup(serverProperty.getCategory());
        settings.setTrackMedalsRequirement(championship.getTrackMedalsRequirement());
        settings.setSafetyRatingRequirement(championship.getSafetyRatingRequirement());
        settings.setRacecraftRatingRequirement(championship.getRacecraftRatingRequirement());
        settings.setPassword(championship.getPassword());
        settings.setSpectatorPassword(championship.getSpectatorPassword());
        settings.setMaxCarSlots(championship.getMaxCarSlots());
        settings.setDumpLeaderboards(championship.getDumpLeaderboards());
        settings.setIsRaceLocked(championship.getIsRaceLocked());
        settings.setRandomizeTrackWhenEmpty(championship.getRandomizeTrackWhenEmpty());
        settings.setCentralEntryListPath("");
        settings.setAllowAutoDq(championship.getAllowAutoDq());
        settings.setShortFormationLap(championship.getFormationLapType());
        settings.setDumpEntryList(championship.getDumpEntryList());
        settings.setFormationLapType(championship.getFormationLapType());
        return settings;
    }

    public Sessions getSessions(Session session) {
        Sessions sessions = new Sessions();
        sessions.setHourOfDay(session.getHourOfDay());
        sessions.setDayOfWeekend(session.getDayOfWeekend());
        sessions.setTimeMultiplier(session.getTimeMultiplier());
        sessions.setSessionType(session.getSessionType());
        sessions.setSessionDurationMinutes(session.getSessionDurationMinutes());
        return sessions;
    }

    public Event getEvent(Race race) {
        Event event = new Event();
        event.setTrack(race.getTrack());
        event.setPreRaceWaitingTimeSeconds(race.getPreRaceWaitingTimeSeconds());
        event.setSessionOverTimeSeconds(race.getSessionOverTimeSeconds());
        event.setAmbientTemp(race.getAmbientTemp());
        event.setCloudLevel(race.getCloudLevel());
        event.setRain(race.getRain());
        event.setWeatherRandomness(race.getWeatherRandomness());
        event.setSessions(race.getSessionList().stream().map(this::getSessions).toList());
        return event;
    }

    public EventRules getEventRules(RaceRules raceRules) {
        EventRules eventRules = new EventRules();
        eventRules.setQualifyStandingType(raceRules.getQualifyStandingType());
        eventRules.setPitWindowLengthSec(raceRules.getPitWindowLengthSec());
        eventRules.setDriverStintTimeSec(raceRules.getDriverStintTimeSec());
        eventRules.setMandatoryPitstopCount(raceRules.getMandatoryPitstopCount());
        eventRules.setMaxTotalDrivingTime(raceRules.getMaxTotalDrivingTime());
        eventRules.setMaxDriversCount(raceRules.getMaxDriversCount());
        eventRules.setIsRefuellingAllowedInRace(raceRules.getIsRefuellingAllowedInRace() == 1);
        eventRules.setIsRefuellingTimeFixed(raceRules.getIsRefuellingTimeFixed() == 1);
        eventRules.setIsMandatoryPitstopRefuellingRequired(raceRules.getIsMandatoryPitstopRefuellingRequired() == 1);
        eventRules.setIsMandatoryPitstopTyreChangeRequired(raceRules.getIsMandatoryPitstopTyreChangeRequired() == 1);
        eventRules.setIsMandatoryPitstopSwapDriverRequired(raceRules.getIsMandatoryPitstopSwapDriverRequired() == 1);
        eventRules.setTyreSetCount(raceRules.getTyreSetCount());
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


    public Entry getEntry(List<Driver> driverList, EntryProperties entryProperties, Grid grid) {
        Entry entry = new Entry();
        entry.setDrivers(driverList);
        entry.setRaceNumber(grid.getCarNumber());
        entry.setForcedCarModel(grid.getCar().getModelId());
        entry.setTeamName(grid.getTeamName());
        entry.setOverrideDriverInfo(entryProperties.getOverrideDriverInfo());
        entry.setDefaultGridPosition(entryProperties.getDefaultGridPosition());
        entry.setBallastKg(entryProperties.getBallastKg());
        entry.setRestrictor(entryProperties.getRestrictor());
        entry.setCustomCar(entryProperties.getCustomCar());
        entry.setOverrideCarModelForCustomCar(entryProperties.getOverrideCarModelForCustomCar());
        entry.setIsServerAdmin(entryProperties.getIsServerAdmin());
        return entry;
    }


    public EntryList getEntryList(List<Entry> entries, EntryListProperties entryListProperties) {
        EntryList entryList = new EntryList();
        entryList.setEntries(entries);
        entryList.setForceEntryList(entryListProperties.getForceEntryList());
        return entryList;
    }

    public Entries getBopEntries(List<Bop> bopList, String raceTrack) {
        Entries entries = new Entries();
        for(Bop bop : bopList) {
            BopEntry bopEntry = new BopEntry();
            bopEntry.setTrack(raceTrack);
            bopEntry.setCarModel(bop.getBopPrimaryKey().getCar().getModelId());
            bopEntry.setBallastKg(bop.getBallastKg());
            bopEntry.setRestrictor(bop.getRestrictor());
            entries.getEntries().add(bopEntry);
        }
        return entries;
    }

    public String findRaceRound(Championship championship, Race race) {
        List<Race> raceList = championship.getRaceList();
        for (int i = 0; i < raceList.size(); i++) {
            if (raceList.get(i).getId().equals(race.getId())) {
                return "Round" + String.valueOf(i+1);
            }
        }
        return "Round" + String.valueOf(0);
    }


}
