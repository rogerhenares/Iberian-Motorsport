package com.iberianmotorsports.service.controller.DTO;

import com.iberianmotorsports.service.model.Grid;

import java.util.List;

public record GridRaceDTO(
        Long points,
        Long firstSector,
        Long secondSector,
        Long thirdSector,
        Long finalTime,
        Integer totalLaps,
        List<SanctionDTO> sanctionDTOList,
        Long raceId,
        Long grid
) {
}
