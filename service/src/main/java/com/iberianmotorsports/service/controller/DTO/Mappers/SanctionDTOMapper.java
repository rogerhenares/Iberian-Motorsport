package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.GridDTO;
import com.iberianmotorsports.service.controller.DTO.SanctionDTO;
import com.iberianmotorsports.service.model.GridRace;
import com.iberianmotorsports.service.model.Sanction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class SanctionDTOMapper implements Function<Sanction, SanctionDTO> {

    GridDTOMapper gridDTOMapper;

    @Override
    public SanctionDTO apply(Sanction sanction){
        GridDTO gridDTO = null;

        if (sanction.getGridRace().getGridRacePrimaryKey().getGrid().getChampionship() != null){
            gridDTO = gridDTOMapper.apply(sanction.getGridRace().getGridRacePrimaryKey().getGrid());
        }

        return new SanctionDTO(
                sanction.getId(),
                sanction.getLap(),
                sanction.getPenalty(),
                sanction.getReason(),
                sanction.getGridRace().getGridRacePrimaryKey().getRace().getId(),
                sanction.getGridRace().getGridRacePrimaryKey().getGrid().getId(),
                gridDTO,
                sanction.getInGame(),
                sanction.getLicensePoints()
        );
    }
}
