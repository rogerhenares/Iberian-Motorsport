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

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CronServiceImpl implements CronService {
    private static final Logger logger = LoggerFactory.getLogger(CronServiceImpl.class);
    private static final Integer OFFSET = 10;
    private static final String ACC_SERVER_EXE_NAME = "accServer.exe";

    private final RaceService raceService;
    private final ExportDataService exportDataService;
    private final ImportDataService importDataService;
    private final ServerProperty serverProperty;

    @Override
    @Scheduled(cron = "0 0/1 * * * *", zone = "Europe/Madrid")
    public void exportAndCreateServerACC() {
        if(serverProperty.getCronEnabled()) {
            logger.info("Launched cron job export");
            List<Race> exportRaceList = raceService.getRaceByStatusAndDate(RaceStatus.PENDING, LocalDateTime.now());
            for (Race raceToBeLaunched: exportRaceList) {
                try {
                    raceService.setRaceStatus(raceToBeLaunched, RaceStatus.EXPORTING);
                    exportDataService.exportData(raceToBeLaunched);
                    File raceFolder = exportDataService.getAccServerDir(raceToBeLaunched);
                    ProcessBuilder processBuilder = new ProcessBuilder(raceFolder.getPath() + File.separator + ACC_SERVER_EXE_NAME);
                    processBuilder.start();
                    raceService.setRaceStatus(raceToBeLaunched, RaceStatus.LAUNCHED);
                } catch (Exception e){
                    raceService.setRaceStatus(raceToBeLaunched, RaceStatus.EXPORT_FAILED);
                }
            }
        }
    }

    @Override
    @Scheduled(cron = "0 0/5 * * * *", zone = "Europe/Madrid")
    public void importServerResultsACC() {
        if(serverProperty.getCronEnabled()){
            logger.info("Launched cron job import");
            List<Race> importRaceList = raceService.getRaceByStatusAndDate(RaceStatus.LAUNCHED, LocalDateTime.now());
            for (Race raceToBeLaunched: importRaceList) {
                int timeOffset = raceToBeLaunched.getSessionList().stream().mapToInt(Session::getSessionDurationMinutes).sum();
                LocalDateTime raceOffset = raceToBeLaunched.getStartDate().plusMinutes(timeOffset).plusMinutes(OFFSET);
                if(raceOffset.isAfter(LocalDateTime.now())){
                    continue;
                }
                try {
                    raceService.setRaceStatus(raceToBeLaunched, RaceStatus.IMPORTING);
                    importDataService.importData(raceToBeLaunched);
                    raceService.setRaceStatus(raceToBeLaunched, RaceStatus.COMPLETED);
                } catch (Exception e){
                    raceService.setRaceStatus(raceToBeLaunched, RaceStatus.IMPORT_FAILED);
                }
            }
        }
    }
}
