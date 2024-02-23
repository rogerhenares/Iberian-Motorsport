package com.iberianmotorsports.service.service.implementation;

import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.Session;
import com.iberianmotorsports.service.model.parsing.properties.ServerProperty;
import com.iberianmotorsports.service.service.*;
import com.iberianmotorsports.service.utils.RaceStatus;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@AllArgsConstructor
public class CronServiceImpl implements CronService {
    private static final Logger logger = LoggerFactory.getLogger(CronServiceImpl.class);
    private static final Integer OFFSET = 10;
    private static final String ACC_SERVER_EXE_NAME = "accServer.exe";

    private final RaceService raceService;
    private final GridRaceService gridRaceService;
    private final ExportDataService exportDataService;
    private final ImportDataService importDataService;
    private final ServerProperty serverProperty;

    @Override
    @Scheduled(cron = "0 0/1 * * * *", zone = "Europe/Madrid")
    public void exportAndCreateServerACC() {
        if(serverProperty.getCronEnabled()) {
            List<Race> exportRaceList = raceService.getRaceByStatusAndDate(RaceStatus.PENDING, LocalDateTime.now());
            for (Race raceToBeLaunched: exportRaceList) {
                try {
                    logger.info("CRON JOB: EXPORTING RACE ID -> {} TRACK -> {}", raceToBeLaunched.getId(), raceToBeLaunched.getTrack());
                    raceService.setRaceStatus(raceToBeLaunched, RaceStatus.EXPORTING);
                    exportDataService.exportData(raceToBeLaunched);
                    File raceFolder = exportDataService.getAccServerDir(raceToBeLaunched);
                    ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "cd /d " + raceFolder +" && "+ACC_SERVER_EXE_NAME);
                    processBuilder.directory(new File(raceFolder.getPath()));
                    logger.info("Working Directory: " + processBuilder.directory().getAbsolutePath());
                    processBuilder.start();
                    logger.info("CRON JOB: LAUNCHED RACE ID -> {} TRACK -> {} DIR -> {}", raceToBeLaunched.getId(), raceToBeLaunched.getTrack(), raceFolder.getPath());
                    raceService.setRaceStatus(raceToBeLaunched, RaceStatus.LAUNCHED);
                } catch (Exception e){
                    raceService.setRaceStatus(raceToBeLaunched, RaceStatus.EXPORT_FAILED);
                    logger.info("CRON JOB: EXPORT FAILED RACE ID -> {} TRACK -> {} ERROR -> {}", raceToBeLaunched.getId(), raceToBeLaunched.getTrack(), e.getMessage());
                }
            }
        }
    }

    @Override
    @Scheduled(cron = "0 0/5 * * * *", zone = "Europe/Madrid")
    public void importServerResultsACC() {
        if(serverProperty.getCronEnabled()){
            List<Race> importRaceList = raceService.getRaceByStatusAndDate(RaceStatus.STOP, LocalDateTime.now());
            for (Race raceToBeLaunched: importRaceList) {
                logger.info("CRON JOB: IMPORTING RACE ID -> {} TRACK -> {}", raceToBeLaunched.getId(), raceToBeLaunched.getTrack());
                int timeOffset = raceToBeLaunched.getSessionList().stream().mapToInt(Session::getSessionDurationMinutes).sum();
                LocalDateTime raceOffset = raceToBeLaunched.getStartDate().plusMinutes(timeOffset).plusMinutes(OFFSET);
                if(raceOffset.isAfter(LocalDateTime.now())){
                    continue;
                }
                try {
                    raceService.setRaceStatus(raceToBeLaunched, RaceStatus.IMPORTING);
                    importDataService.importData(raceToBeLaunched);
                    gridRaceService.calculateDropRoundForRaceChampionship(raceToBeLaunched);
                    raceService.setRaceStatus(raceToBeLaunched, RaceStatus.COMPLETED);
                    logger.info("CRON JOB: IMPORTING COMPLETED RACE ID -> {} TRACK -> {}", raceToBeLaunched.getId(), raceToBeLaunched.getTrack());
                } catch (Exception e){
                    raceService.setRaceStatus(raceToBeLaunched, RaceStatus.IMPORT_FAILED);
                    logger.info("CRON JOB: IMPORTING FAILED RACE ID -> {} TRACK -> {} ERROR -> {}", raceToBeLaunched.getId(), raceToBeLaunched.getTrack(), e.getMessage());
                }
            }
        }
    }
}
