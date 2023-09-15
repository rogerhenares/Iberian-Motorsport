package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.GridRace;
import com.iberianmotorsports.service.model.composeKey.GridRacePrimaryKey;
import com.iberianmotorsports.service.model.parsing.imports.Results;
import com.iberianmotorsports.service.service.ChampionshipService;
import com.iberianmotorsports.service.service.GridRaceService;
import com.iberianmotorsports.service.service.ImportDataService;
import com.iberianmotorsports.service.service.RaceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class ImportDataServiceImpl implements ImportDataService {

    @Autowired
    private ChampionshipService championshipService;

    @Autowired
    private final RaceService raceService;

    @Autowired
    private final GridRaceService gridRaceService;

    @Override
    public void importData() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String filePath = "230902_220657_Q.json";

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_16LE)) {

            Results results = objectMapper.readValue(inputStreamReader, Results.class);

            String fakeServerName = "IML MotorSport | GT World Tour | Suzuka | #C329R368";

            Long championshipID = getChampionshipID(fakeServerName);
            Long raceID = getRaceID(fakeServerName);

            Championship championship = championshipService.findChampionshipById(championshipID);

            championship.getGridList().stream()
                    .map(grid -> results.getSessionResult().getLeaderBoardLines().stream()
                            .filter(leaderBoardLine -> leaderBoardLine.getCar().getRaceNumber().equals(Float.valueOf(grid.getCarNumber())))
                            .map(leaderBoardLine -> {
                                GridRace gridRace = new GridRace();
                                GridRacePrimaryKey gridRacePrimaryKey = new GridRacePrimaryKey(grid, raceService.findRaceById(raceID));
                                gridRace.setGridRacePrimaryKey(gridRacePrimaryKey);
                                if (Objects.equals(results.getSessionType(), "Q")) {
                                    gridRace.setQualyTime(leaderBoardLine.getTiming().getTotalTime().longValue());
                                    gridRace.setQualyFirstSector(leaderBoardLine.getTiming().getBestSplits().get(0).longValue());
                                    gridRace.setQualySecondSector(leaderBoardLine.getTiming().getBestSplits().get(1).longValue());
                                    gridRace.setQualyThirdSector(leaderBoardLine.getTiming().getBestSplits().get(2).longValue());
                                }
                                if (Objects.equals(results.getSessionType(), "R")) {
                                    gridRace.setFirstSector(leaderBoardLine.getTiming().getBestSplits().get(0).longValue());
                                    gridRace.setSecondSector(leaderBoardLine.getTiming().getBestSplits().get(1).longValue());
                                    gridRace.setThirdSector(leaderBoardLine.getTiming().getBestSplits().get(2).longValue());
                                    gridRace.setFinalTime(leaderBoardLine.getTiming().getTotalTime().longValue());
                                    gridRace.setTotalLaps(leaderBoardLine.getTiming().getLapCount().intValue());
                                }

                                // TODO pasar los index de los sectores a una variable estática y los Q y R que sean un enum.
                                // TODO separar los set para q y r en métodos aparte.
                                // TODO El filepath desde el properties.
                                // TODO leer y parsear el serverName real
                                // TODO agregar las penalizaciones
                                // TODO encontrar la mejor vuelta de entre todos los corredores (compara el bestSplits de arriba con los bestSplits de cada corredor)
                                // TODO vienen ordenados (en race el va por leaderboard, en Q por mejor vuelta)

                                return gridRaceService.saveGridRace(gridRace);
                            }
                        )
                    );
        }
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

}
