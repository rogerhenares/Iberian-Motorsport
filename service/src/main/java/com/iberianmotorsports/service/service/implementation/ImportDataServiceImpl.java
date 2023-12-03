package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.model.GridRace;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.Sanction;
import com.iberianmotorsports.service.model.composeKey.GridRacePrimaryKey;
import com.iberianmotorsports.service.model.parsing.imports.Lap;
import com.iberianmotorsports.service.model.parsing.imports.LeaderBoardLine;
import com.iberianmotorsports.service.model.parsing.imports.Penalty;
import com.iberianmotorsports.service.model.parsing.imports.Results;
import com.iberianmotorsports.service.model.parsing.properties.ServerProperty;
import com.iberianmotorsports.service.repository.SanctionRepository;
import com.iberianmotorsports.service.service.GridRaceService;
import com.iberianmotorsports.service.service.ImportDataService;
import com.iberianmotorsports.service.service.RaceService;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.iberianmotorsports.service.service.implementation.ExportDataServiceImpl.SERVER_FOLDER_SEPARATOR;

@Service
@Transactional
@AllArgsConstructor
public class ImportDataServiceImpl implements ImportDataService {

    private static final Logger logger = LoggerFactory.getLogger(ImportDataServiceImpl.class);
    private static final String RESULT_FOLDER_NAME = "results";
    private static final Long DEFAULT_ACC_ZERO_VALUE = 2000000000L;

    private final RaceService raceService;
    private final GridRaceService gridRaceService;
    private final SanctionRepository sanctionRepository;
    private final ServerProperty serverProperty;

    @Override
    public void importData(Race race) throws Exception {
        if (Objects.isNull(race.getChampionship())){
            race = raceService.findRaceById(race.getId());
        }
        ObjectMapper objectMapper = new ObjectMapper();

        File championshipFolder = new File(serverProperty.getFolder() + File.separator + "C" + race.getChampionship().getId() + SERVER_FOLDER_SEPARATOR + race.getChampionship().getName());
        if(!championshipFolder.isDirectory()) {
            throw new ServiceException(String.format("Folder for championship -> {} not found path -> {}", race.getId(), championshipFolder.getAbsolutePath()));
        }
        File raceFolderResults = new File(championshipFolder.getAbsolutePath() + File.separator + "R" + race.getId() + SERVER_FOLDER_SEPARATOR + race.getTrack() + File.separator + RESULT_FOLDER_NAME);
        if(!raceFolderResults.isDirectory()) {
            throw new ServiceException(String.format("Folder for race -> {} not found path -> {}", race.getId(), raceFolderResults.getAbsolutePath()));
        }

        Race finalRace = race;
        File raceResultFile = Arrays.stream(raceFolderResults.listFiles()).filter(file -> file.getName().contains("R")).findFirst()
                .orElseThrow(() -> new ServiceException(String.format("Race Result file not found for race -> {} path -> {}", finalRace.getId(), raceFolderResults.getAbsolutePath())));
        File qualyResultFile = Arrays.stream(raceFolderResults.listFiles()).filter(file -> file.getName().contains("Q")).findFirst()
                .orElseThrow(() -> new ServiceException(String.format("Qualy Result file not found for race -> {} path -> {}", finalRace.getId(), raceFolderResults.getAbsolutePath())));

        try (FileInputStream fisRace = new FileInputStream(raceResultFile.getAbsolutePath());
             FileInputStream fisQualy = new FileInputStream(qualyResultFile.getAbsolutePath());
             InputStreamReader isrRace = new InputStreamReader(fisRace, StandardCharsets.UTF_16LE);
             InputStreamReader isrQualy = new InputStreamReader(fisQualy, StandardCharsets.UTF_16LE)) {

            Results raceResults = objectMapper.readValue(isrRace, Results.class);
            Results qualyResults = objectMapper.readValue(isrQualy, Results.class);

            createGridRaceFromResults(race, qualyResults);
            createGridRaceFromResults(race, raceResults);
            createEmptyGridRaces(race, raceResults);
            gridRaceService.calculateDropRoundForRaceChampionship(race);
        }
    }

    @Override
    public boolean isResultsReady(Race race) {
        File championshipFolder = new File(serverProperty.getFolder() + File.separator + "C" + race.getChampionship().getId() + SERVER_FOLDER_SEPARATOR + race.getChampionship().getName());
        File raceFolderResults = new File(championshipFolder.getAbsolutePath() + File.separator + "R" + race.getId() + SERVER_FOLDER_SEPARATOR + race.getTrack() + File.separator + RESULT_FOLDER_NAME);
        try{
            return Arrays.stream(Objects.requireNonNull(raceFolderResults.listFiles())).anyMatch(file -> file.getName().contains("R"));
        } catch (Exception e) {
            return false;
        }
    }

    private void createGridRaceFromResults(Race race, Results results) {
        AtomicInteger index = new AtomicInteger(0);
        Map<Integer, Integer> carPosition = new HashMap<>();
        GridRace winnerLapTime = new GridRace();
        results.getSessionResult().getLeaderBoardLines().stream()
                .map(entry -> {
                    carPosition.put(entry.getCar().getRaceNumber(), index.get());
                    if(index.get() == 0) {
                        winnerLapTime.setFinalTime(entry.getTiming().getTotalTime().longValue());
                    }
                    index.incrementAndGet();
                    return entry;
                }).toList();
        Lap bestLap = findBestLap(results);
        race.getChampionship().getGridList().stream()
                .map(grid -> results.getSessionResult().getLeaderBoardLines().stream()
                        .filter(leaderBoardLine -> leaderBoardLine.getCar().getRaceNumber().equals(grid.getCarNumber()))
                        .map(leaderBoardLine -> {
                            GridRace gridRace = getGridRace(race, grid);
                            if (Objects.equals(results.getSessionType(), "Q")) {
                                setQualyData(gridRace, leaderBoardLine, carPosition.get(grid.getCarNumber()), results.getLaps());
                            }
                            if (Objects.equals(results.getSessionType(), "R")) {
                                setRaceData(gridRace, leaderBoardLine, carPosition.get(grid.getCarNumber()), bestLap, results.getLaps(), winnerLapTime);
                            }
                            //results.getPenalties().stream()
                            //        .filter(penalty -> penalty.getCarId().equals(Float.valueOf(leaderBoardLine.getCar().getCarId())))
                            //        .forEach(penalty -> importSanctions(gridRace, penalty));
                            return gridRace;
                        })
                        .collect(Collectors.toList())
                )
                .flatMap(List::stream)
                .forEach(gridRace -> {});
    }

    private GridRace getGridRace(Race race, Grid grid) {
        GridRace gridRace;
        try {
            gridRace = gridRaceService.getGridRace(grid.getId(), race.getId());
        } catch (ServiceException sE) {
            gridRace = new GridRace();
            GridRacePrimaryKey gridRacePrimaryKey = new GridRacePrimaryKey(grid, race);
            gridRace.setGridRacePrimaryKey(gridRacePrimaryKey);
        }
        return gridRace;
    }

    private void createEmptyGridRaces(Race race, Results results){

        List<Integer> resultsCarNumbers = results.getSessionResult().getLeaderBoardLines().stream()
                .map(entry -> entry.getCar().getRaceNumber())
                .toList();

        List<Grid> missingGrids = race.getChampionship().getGridList().stream()
                .filter(grid -> !resultsCarNumbers.contains(grid.getCarNumber()))
                .toList();

        missingGrids.forEach(grid -> generateEmptyGridRace(grid, race));
    }

    private void generateEmptyGridRace(Grid grid, Race race) {
        GridRace gridRace = new GridRace();
        GridRacePrimaryKey gridRacePrimaryKey = new GridRacePrimaryKey(grid, race);
        gridRace.setGridRacePrimaryKey(gridRacePrimaryKey);
        gridRace.setFinalTime(0L);
        gridRace.setFirstSector(0L);
        gridRace.setSecondSector(0L);
        gridRace.setThirdSector(0L);
        gridRace.setQualyTime(0L);
        gridRace.setQualyFirstSector(0L);
        gridRace.setQualySecondSector(0L);
        gridRace.setQualyThirdSector(0L);
        gridRace.setTotalLaps(0);
        gridRace.setQualyPosition(-1);
        gridRace.setPoints(0L);
        GridRace savedGridRace = gridRaceService.saveGridRace(gridRace);
        savedGridRace.getGridRacePrimaryKey().getGrid().getGridRaceList().add(gridRace);
    }

    private Long getChampionshipID(String serverName) {
        int positionC = serverName.indexOf('C');
        int positionR = serverName.indexOf('R');

        String championshipIDWithC = serverName.substring(positionC, positionR);
        String championshipID = championshipIDWithC.replaceAll("[^\\d.]", "");

        return Long.parseLong(championshipID);
    }

    private Long getRaceID(String serverName) {
        int positionR = serverName.indexOf('R');

        String raceIDWithR = serverName.substring(positionR);
        String raceID = raceIDWithR.replaceAll("[^\\d.]", "");

        return Long.parseLong(raceID);
    }

    private void setRaceData(GridRace gridRace, LeaderBoardLine leaderBoardLine, int position, Lap bestLap, List<Lap> laps, GridRace winner) {
        gridRace.setFinalTime(leaderBoardLine.getTiming().getTotalTime().longValue() >= DEFAULT_ACC_ZERO_VALUE ? 0 : leaderBoardLine.getTiming().getTotalTime().longValue());
        gridRace.setTotalLaps(leaderBoardLine.getTiming().getLapCount());
        Optional<Lap> bestLapSplits = laps.stream().filter(lap -> lap.getCarId().equals(leaderBoardLine.getCar().getCarId()))
                .filter(Lap::getIsValidForBest)
                .filter(lap -> lap.getLapTime().equals(leaderBoardLine.getTiming().getBestLap()))
                .findFirst();
        gridRace.setFirstSector(bestLapSplits.isEmpty() || bestLapSplits.get().getSplits().get(0).longValue() == DEFAULT_ACC_ZERO_VALUE ? 0 : bestLapSplits.get().getSplits().get(0).longValue());
        gridRace.setSecondSector(bestLapSplits.isEmpty() || bestLapSplits.get().getSplits().get(1).longValue() == DEFAULT_ACC_ZERO_VALUE ? 0 : bestLapSplits.get().getSplits().get(1).longValue());
        gridRace.setThirdSector(bestLapSplits.isEmpty() || bestLapSplits.get().getSplits().get(2).longValue() == DEFAULT_ACC_ZERO_VALUE ? 0 : bestLapSplits.get().getSplits().get(2).longValue());
        gridRace.setFastLap(Objects.equals(bestLap.getCarId(), leaderBoardLine.getCar().getCarId()));
        gridRaceService.calculatePointsToGridRace(gridRace, position, winner);
        gridRaceService.saveGridRace(gridRace);
    }

    private void setQualyData(GridRace gridRace, LeaderBoardLine leaderBoardLine, int position, List<Lap> laps) {
        gridRace.setQualyTime(leaderBoardLine.getTiming().getBestLap().longValue());
        Optional<Lap> bestLapSplits = laps.stream().filter(lap -> lap.getCarId().equals(leaderBoardLine.getCar().getCarId()))
                .filter(Lap::getIsValidForBest)
                .filter(lap -> lap.getLapTime().equals(leaderBoardLine.getTiming().getBestLap()))
                .findFirst();
        gridRace.setQualyFirstSector(bestLapSplits.isPresent() ? bestLapSplits.get().getSplits().get(0).longValue() : 0);
        gridRace.setQualySecondSector(bestLapSplits.isPresent() ? bestLapSplits.get().getSplits().get(1).longValue(): 0);
        gridRace.setQualyThirdSector(bestLapSplits.isPresent() ? bestLapSplits.get().getSplits().get(2).longValue(): 0);
        gridRace.setQualyPosition(position);
        gridRaceService.saveGridRace(gridRace);
    }

    private Sanction importSanctions(GridRace gridRace, Penalty penalty) {
        Sanction sanction = new Sanction();
        sanction.setGridRace(gridRace);
        sanction.setLap(penalty.getViolationInLap().intValue());
        sanction.setPenalty(penalty.getPenalty());
        sanction.setReason(penalty.getReason());
        sanction.setInGame(true);
        return sanctionRepository.save(sanction);
    }

    private Lap findBestLap(Results results) {
        Integer bestLap = results.getSessionResult().getBestLap();
        return results.getLaps().stream()
                .filter(Lap::getIsValidForBest)
                .filter(lap -> lap.getLapTime().equals(bestLap)).findFirst()
                .orElseThrow(()->new ServiceException(ErrorMessages.IMPORT_UNABLE_TO_GET_FAST_LAP.getDescription()));
    }

}
