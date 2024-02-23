package com.iberianmotorsports.service.service.implementation;

import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.Session;
import com.iberianmotorsports.service.model.parsing.properties.ServerProperty;
import com.iberianmotorsports.service.service.*;
import com.iberianmotorsports.service.utils.RaceStatus;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

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
    @Scheduled(cron = "0 0/1 * * * *", zone = "Europe/Madrid")
    public void stopServerACC() {
        if(serverProperty.getCronEnabled()){
            List<Race> launchedRaceList = raceService.getRaceByStatusAndDate(RaceStatus.LAUNCHED, LocalDateTime.now());
            for (Race raceToStop: launchedRaceList) {
                if(importDataService.isResultsReady(raceToStop)) {
                    logger.info("CRON JOB: STOP RACE ID -> {} TRACK -> {}", raceToStop.getId(), raceToStop.getTrack());
                    ProcessBuilder getPidProcessBuilder = new ProcessBuilder("cmd.exe", "/c", "netstat -aon | findstr :9262");

                    try {
                        Process portProcess = getPidProcessBuilder.start();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(portProcess.getInputStream()));
                        String line;
                        String serverPID="";
                        while ((line = reader.readLine()) != null) {
                            if (line.contains(":9262")) {
                                String[] parts = line.trim().split("\\s+");
                                if (parts.length >= 5) {
                                    serverPID = parts[parts.length - 1];
                                }
                            }
                        }
                        if(serverPID.isBlank()) {
                            logger.info("CRON JOB: STOP PROCESS FAILED RACE ID -> {}", raceToStop.getId());
                        }else{
                            ProcessBuilder stopServerBuilder = new ProcessBuilder("cmd.exe", "/c", "taskkill /f /pid " + serverPID);
                            stopServerBuilder.start();
                            raceService.setRaceStatus(raceToStop, RaceStatus.STOP);
                        }
                    } catch (IOException e) {
                        logger.info("CRON JOB: STOP FAILED RACE ID -> {} TRACK -> {} ERROR -> {}", raceToStop.getId(), raceToStop.getTrack(), e.getMessage());
                        raceService.setRaceStatus(raceToStop, RaceStatus.STOP_FAILED);
                    }
                }
            }
        }
    }

    @Override
    @Scheduled(cron = "0 0/1 * * * *", zone = "Europe/Madrid")
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
