package com.iberianmotorsports.service.controller.DTO;

import java.util.List;

public record GridRaceDTO(
        Long points,
        Long firstSector,
        Long secondSector,
        Long thirdSector,
        Long finalTime,
        Integer totalLaps,
        Long qualyTime,
        Long qualyFirstSector,
        Long qualySecondSector,
        Long qualyThirdSector,
        Integer qualyPosition,
        Integer sanctionTime,
        List<SanctionDTO> sanctionDTOList,
        Boolean dropRound,
        Long raceId,
        Long gridId,
        GridDTO grid,
        Boolean dsqRound
) {
}
