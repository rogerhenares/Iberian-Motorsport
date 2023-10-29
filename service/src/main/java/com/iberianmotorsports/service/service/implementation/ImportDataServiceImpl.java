package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.iberianmotorsports.service.service.ChampionshipService;
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
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
// TODO cambiar los distintos estados del RACE :D
// TODO Mejora pasar los index de los sectores a una variable estática y los Q y R que sean un enum.
public class ImportDataServiceImpl implements ImportDataService {

    private final RaceService raceService;
    private final GridRaceService gridRaceService;
    private final ChampionshipService championshipService;
    private final SanctionRepository sanctionRepository;

    private final ServerProperty serverProperty;

    @Value("#{'${pointsSystem}'}")
    private List<Integer> pointsSystem;

    @Value("#{'${qualyPoints}'}")
    private List<Integer> qualyPoints;

    @Override
    public void importData(Race race) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        File championshipFolder = new File(new URI(serverProperty.getFolder() + File.separator + "C" + race.getChampionship().getId() + "-" + race.getChampionship().getName()));
        if(!championshipFolder.isDirectory()) {
            throw new ServiceException(String.format("Folder for championship -> {} not found path -> {}", race.getChampionship().getId(), championshipFolder.getAbsolutePath()));
        }
        File raceFolderResults = new File(championshipFolder.getAbsolutePath() + File.separator + "R" + race.getId() + "-" + race.getTrack() + File.separator + "results");
        if(!raceFolderResults.isDirectory()) {
            throw new ServiceException(String.format("Folder for race -> {} not found path -> {}", race.getId(), raceFolderResults.getAbsolutePath()));
        }

        File raceResultFile = Arrays.stream(raceFolderResults.listFiles()).filter(file -> file.getName().contains("R")).findFirst()
                .orElseThrow(() -> new ServiceException(String.format("Race Result file not found for race -> {} path -> {}", race.getId(), raceFolderResults.getAbsolutePath())));
        File qualyResultFile = Arrays.stream(raceFolderResults.listFiles()).filter(file -> file.getName().contains("Q")).findFirst()
                .orElseThrow(() -> new ServiceException(String.format("Qualy Result file not found for race -> {} path -> {}", race.getId(), raceFolderResults.getAbsolutePath())));

        try (FileInputStream fisRace = new FileInputStream(raceResultFile.getAbsolutePath());
             FileInputStream fisQualy = new FileInputStream(qualyResultFile.getAbsolutePath());
             InputStreamReader isrRace = new InputStreamReader(fisRace, StandardCharsets.UTF_16LE);
             InputStreamReader isrQualy = new InputStreamReader(fisQualy, StandardCharsets.UTF_16LE)) {

            Results raceResults = objectMapper.readValue(isrRace, Results.class);
            Results qualyResults = objectMapper.readValue(isrQualy, Results.class);

            createGridRaceFromResults(race, qualyResults);
            createGridRaceFromResults(race, raceResults);
        }
    }

    private void createGridRaceFromResults(Race race, Results results) {
        AtomicInteger index = new AtomicInteger(0);
        Lap bestLap = findBestLap(results);
        race.getChampionship().getGridList().stream()
                .map(grid -> results.getSessionResult().getLeaderBoardLines().stream()
                        .filter(leaderBoardLine -> leaderBoardLine.getCar().getRaceNumber().equals(Float.valueOf(grid.getCarNumber())))
                        .map(leaderBoardLine -> {
                            GridRace gridRace = new GridRace();
                            GridRacePrimaryKey gridRacePrimaryKey = new GridRacePrimaryKey(grid, raceService.findRaceById(race.getId()));
                            gridRace.setGridRacePrimaryKey(gridRacePrimaryKey);
                            if (Objects.equals(results.getSessionType(), "Q")) {
                                setQualyData(gridRace, leaderBoardLine, index.get());
                            }
                            if (Objects.equals(results.getSessionType(), "R")) {
                                setRaceData(gridRace, leaderBoardLine, index.get(), bestLap);
                            }
                            results.getPenalties().stream()
                                    .filter(penalty -> penalty.getCarId().equals(leaderBoardLine.getCar().getCarId()))
                                    .forEach(penalty -> importSanctions(gridRace, penalty));

                            index.incrementAndGet();

                            return gridRace;
                        })
                        .collect(Collectors.toList())
                )
                .flatMap(List::stream)
                .forEach(gridRace -> {});
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
        gridRace.setFirstSector(leaderBoardLine.getTiming().getBestSplits().get(0).longValue());
        gridRace.setSecondSector(leaderBoardLine.getTiming().getBestSplits().get(1).longValue());
        gridRace.setThirdSector(leaderBoardLine.getTiming().getBestSplits().get(2).longValue());
        gridRace.setFinalTime(leaderBoardLine.getTiming().getTotalTime().longValue());
        gridRace.setTotalLaps(leaderBoardLine.getTiming().getLapCount().intValue());
        long points  = pointsSystem.get(position).longValue();
        if (Objects.equals(bestLap.getCarId(), leaderBoardLine.getCar().getCarId()))
        { points += 1; }
        gridRace.setPoints(points);
        return gridRaceService.saveGridRace(gridRace);
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
        return gridRaceService.saveGridRace(gridRace);
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
