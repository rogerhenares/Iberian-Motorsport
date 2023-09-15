package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.ChampionshipCategoryDTO;
import com.iberianmotorsports.service.controller.DTO.ChampionshipDTO;
import com.iberianmotorsports.service.model.Championship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Function;

@Service
public class ChampionshipDTOMapper implements Function<Championship, ChampionshipDTO> {

    @Autowired
    RaceDTOMapper raceDTOMapper;

    @Autowired
    ChampionshipCategoryDTOMapper championshipCategoryDTOMapper;

    @Autowired
    CarDTOMapper carDTOMapper;

    @Override
    public ChampionshipDTO apply(Championship championship) {
        return new ChampionshipDTO(
                championship.getId(),
                championship.getName(),
                championship.getDescription(),
                championship.getAdminPassword(),
                championship.getCarGroup(),
                championship.getTrackMedalsRequirement(),
                championship.getSafetyRatingRequirement(),
                championship.getRacecraftRatingRequirement(),
                championship.getPassword(),
                championship.getSpectatorPassword(),
                championship.getMaxCarSlots(),
                championship.getDumpLeaderboards(),
                championship.getIsRaceLocked(),
                championship.getRandomizeTrackWhenEmpty(),
                championship.getCentralEntryListPath(),
                championship.getAllowAutoDq(),
                championship.getShortFormationLap(),
                championship.getDumpEntryList(),
                championship.getFormationLapType(),
                championship.getIgnorePrematureDisconnects(),
                championship.getImageContent(),
                championship.getStartDate(),
                championship.getRaceList().stream().map(raceDTOMapper).toList(),
                championship.getDisabled(),
                championship.getStarted(),
                championship.getFinished(),
                championship.getCategoryList().stream().map(championshipCategoryDTOMapper).toList(),
                !Objects.isNull(championship.getCarListForChampionship()) ?
                        championship.getCarListForChampionship().stream().map(carDTOMapper).toList() :
                        null
        );
    }
}
