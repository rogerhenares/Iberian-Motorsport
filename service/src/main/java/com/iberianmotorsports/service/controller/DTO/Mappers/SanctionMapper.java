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
        sanction.setPenalty(sanctionDTO.penalty());
        sanction.setReason(sanctionDTO.reason());
        sanction.setGridRace(getGridRace(sanctionDTO.gridId(), sanctionDTO.raceId()));
        sanction.setInGame(sanctionDTO.inGame());
        sanction.setLicensePoints(sanctionDTO.licensePoints());
        return sanction;
    }

}
