package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.GridRaceDTO;
import com.iberianmotorsports.service.model.GridRace;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GridRaceDTOMapper implements Function<GridRace, GridRaceDTO> {

    @Autowired
    SanctionDTOMapper sanctionDTOMapper;

    @Override
    public GridRaceDTO apply(GridRace gridRace){
        return new GridRaceDTO(
                gridRace.getPoints(),
                gridRace.getFirstSector(),
                gridRace.getSecondSector(),
                gridRace.getThirdSector(),
                gridRace.getFinalTime(),
                gridRace.getTotalLaps(),
                gridRace.getSanctionList().stream().map(sanctionDTOMapper).toList(),
                gridRace.getGridRacePrimaryKey().getRace().getId(),
                gridRace.getGridRacePrimaryKey().getGrid().getId()
                );
    }
}
