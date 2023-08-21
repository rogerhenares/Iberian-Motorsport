package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.GridRaceDTO;
import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.model.GridRace;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static com.iberianmotorsports.service.utils.GetGridRace.getGridRace;

@Service
@AllArgsConstructor
public class GridRaceMapper implements Function<GridRaceDTO, GridRace> {

    @Autowired
    SanctionMapper sanctionMapper;

    @Override
    public GridRace apply(GridRaceDTO gridRaceDTO) {
        GridRace gridRace = new GridRace();
        gridRace.setPoints(gridRaceDTO.points());
        gridRace.setFinalTime(gridRaceDTO.finalTime());
        gridRace.setFirstSector(gridRaceDTO.firstSector());
        gridRace.setSecondSector(gridRaceDTO.secondSector());
        gridRace.setThirdSector(gridRaceDTO.thirdSector());
        gridRace.setTotalLaps(gridRaceDTO.totalLaps());
        gridRace.setSanctionList(gridRaceDTO.sanctionDTOList()
                        .stream()
                        .map(sanctionMapper).toList());
        gridRace.setGridRacePrimaryKey(getGridRace(gridRaceDTO.gridId(), gridRaceDTO.raceId()).getGridRacePrimaryKey());
        return gridRace;
    }




}
