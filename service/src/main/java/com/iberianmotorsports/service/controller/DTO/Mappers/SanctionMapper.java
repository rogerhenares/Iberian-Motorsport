package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.SanctionDTO;
import com.iberianmotorsports.service.model.Sanction;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.function.Function;

import static com.iberianmotorsports.service.utils.GetGridRace.getGridRace;

@Service
@AllArgsConstructor
public class SanctionMapper implements Function<SanctionDTO, Sanction> {

    @Override
    public Sanction apply(SanctionDTO sanctionDTO) {
        Sanction sanction = new Sanction();
        sanction.setId(sanctionDTO.id());
        sanction.setLap(sanctionDTO.lap());
        sanction.setSector(sanctionDTO.sector());
        sanction.setTime(sanctionDTO.time());
        sanction.setPenalty(sanctionDTO.penalty());
        sanction.setDescription(sanctionDTO.description());
        sanction.setGridRace(getGridRace(sanctionDTO.gridId(), sanctionDTO.raceId()));
        return sanction;
    }


}
