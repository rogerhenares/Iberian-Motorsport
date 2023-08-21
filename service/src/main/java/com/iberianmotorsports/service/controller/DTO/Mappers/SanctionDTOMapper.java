package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.SanctionDTO;
import com.iberianmotorsports.service.model.Sanction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class SanctionDTOMapper implements Function<Sanction, SanctionDTO> {

    @Override
    public SanctionDTO apply(Sanction sanction){
        return new SanctionDTO(
                sanction.getId(),
                sanction.getTime(),
                sanction.getLap(),
                sanction.getSector(),
                sanction.getPenalty(),
                sanction.getDescription(),
                sanction.getGridRace().getGridRacePrimaryKey().getRace().getId(),
                sanction.getGridRace().getGridRacePrimaryKey().getGrid().getId()
        );
    }
}
