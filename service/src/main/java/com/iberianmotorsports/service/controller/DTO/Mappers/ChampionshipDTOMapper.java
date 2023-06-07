package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.ChampionshipDTO;
import com.iberianmotorsports.service.model.Championship;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ChampionshipDTOMapper implements Function<Championship, ChampionshipDTO> {
    @Override
    public ChampionshipDTO apply(Championship championship) {
        return new ChampionshipDTO(
                championship.getName(),
                championship.getDescription(),
                championship.getAdmin_password(),
                championship.getCarGroup(),
                championship.getTrackMedalsRequirement(),
                championship.getSafetyRatingRequirement(),
                championship.getRacecraftRatingRequirement(),
                championship.getPassword(),
                championship.getSpectatorPassword(),
                championship.getMaxCarSlots(),
                championship.getDumpLeaderboards(),
                championship.getIsRaceLocked(),
                championship.getImageContent()
        );
    }
}
