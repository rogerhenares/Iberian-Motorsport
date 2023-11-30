package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.beans.factory.annotation.Value;
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

    private static final String RESULT_FOLDER_NAME = "results";
    private static final Long DEFAULT_ACC_ZERO_VALUE = 2147483648L;

    private final RaceService raceService;
    private final GridRaceService gridRaceService;
    private final SanctionRepository sanctionRepository;
    private final ServerProperty serverProperty;

    @Value("#{'${pointsSystem}'}")
    private List<Integer> pointsSystem;

    @Value("#{'${qualyPoints}'}")
    private List<Integer> qualyPoints;

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
        results.getSessionResult().getLeaderBoardLines().stream()
                .map(entry -> {
                    carPosition.put(entry.getCar().getRaceNumber(), index.get());
                    index.incrementAndGet();
                    return entry;
                }).toList();
        Lap bestLap = findBestLap(results);
        race.getChampionship().getGridList().stream()
                .map(grid -> results.getSessionResult().getLeaderBoardLines().stream()
                        .filter(leaderBoardLine -> leaderBoardLine.getCar().getRaceNumber().equals(grid.getCarNumber()))
                        .map(leaderBoardLine -> {
                            GridRace gridRace = new GridRace();
                            GridRacePrimaryKey gridRacePrimaryKey = new GridRacePrimaryKey(grid, raceService.findRaceById(race.getId()));
                            gridRace.setGridRacePrimaryKey(gridRacePrimaryKey);
                            if (Objects.equals(results.getSessionType(), "Q")) {
                                gridRace = setQualyData(gridRace, leaderBoardLine, carPosition.get(grid.getCarNumber()));
                            }
                            if (Objects.equals(results.getSessionType(), "R")) {
                                setRaceData(gridRace, leaderBoardLine, carPosition.get(grid.getCarNumber()), bestLap);
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

    private void createEmptyGridRaces(Race race, Results results){

        List<Integer> resultsCarNumbers = results.getSessionResult().getLeaderBoardLines().stream()
                .map(entry -> entry.getCar().getRaceNumber())
                .toList();

        List<Grid> missingGrids = race.getChampionship().getGridList().stream()
                .filter(grid -> !resultsCarNumbers.contains(grid.getCarNumber()))
                .toList();


        missingGrids.forEach(grid -> { generateEmptyGridRace(grid, race);
        });
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
        gridRace.setQualyPosition(0);
        gridRace.setPoints(0L);
        GridRace savedGridRace = gridRaceService.saveGridRace(gridRace);
        savedGridRace.getGridRacePrimaryKey().getGrid().getGridRaceList().add(gridRace);
        gridRaceService.calculateDropRoundForGrid(grid);


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

    private GridRace setRaceData(GridRace gridRace, LeaderBoardLine leaderBoardLine, int position, Lap bestLap) {
        gridRace.setFirstSector(leaderBoardLine.getTiming().getBestSplits().get(0).longValue() == DEFAULT_ACC_ZERO_VALUE ? 0 : leaderBoardLine.getTiming().getBestSplits().get(0).longValue());
        gridRace.setSecondSector(leaderBoardLine.getTiming().getBestSplits().get(1).longValue() == DEFAULT_ACC_ZERO_VALUE ? 0 : leaderBoardLine.getTiming().getBestSplits().get(1).longValue());
        gridRace.setThirdSector(leaderBoardLine.getTiming().getBestSplits().get(2).longValue() == DEFAULT_ACC_ZERO_VALUE ? 0 : leaderBoardLine.getTiming().getBestSplits().get(2).longValue());
        gridRace.setFinalTime(leaderBoardLine.getTiming().getTotalTime().longValue() >= DEFAULT_ACC_ZERO_VALUE ? 0 : leaderBoardLine.getTiming().getTotalTime().longValue());
        gridRace.setTotalLaps(leaderBoardLine.getTiming().getLapCount().intValue());
        long points  = pointsSystem.get(position).longValue();
        if (Objects.equals(bestLap.getCarId(), Float.valueOf(leaderBoardLine.getCar().getCarId()))) { points += 1; }
        gridRace.setPoints(points);
        GridRace savedGridRace = gridRaceService.saveGridRace(gridRace);
        savedGridRace.getGridRacePrimaryKey().getGrid().getGridRaceList().add(gridRace);
        gridRaceService.calculateDropRoundForGrid(savedGridRace.getGridRacePrimaryKey().getGrid());

        return savedGridRace;
    }

    private GridRace setQualyData(GridRace gridRace, LeaderBoardLine leaderBoardLine, int position) {
        gridRace.setQualyTime(leaderBoardLine.getTiming().getTotalTime().longValue());
        gridRace.setQualyFirstSector(leaderBoardLine.getTiming().getBestSplits().get(0).longValue());
        gridRace.setQualySecondSector(leaderBoardLine.getTiming().getBestSplits().get(1).longValue());
        gridRace.setQualyThirdSector(leaderBoardLine.getTiming().getBestSplits().get(2).longValue());
        if (position <= 2) {
            long points = qualyPoints.get(position).longValue();
            gridRace.setPoints(points);
        }
        return gridRace;
        //gridRaceService.saveGridRace(gridRace);
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
        Lap bestLap = new Lap();
        for (Lap lap : results.getLaps()) {
            if (bestLap.getLapTime() == null) {
                bestLap = lap;
            }
            if (lap.getLapTime() < bestLap.getLapTime()) {
                bestLap = lap;
            }
        }
        bestLap.setIsValidForBest(true);
        return bestLap;
    }

}
