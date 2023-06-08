package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.ChampionshipDTO;
import com.iberianmotorsports.service.model.Championship;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ChampionshipMapper implements Function<ChampionshipDTO, Championship> {
    @Override
    public Championship apply(ChampionshipDTO championshipDTO) {
        Championship championship = new Championship();
        championship.setId(championshipDTO.id());
        championship.setName(championshipDTO.name());
        championship.setDescription(championshipDTO.description());
        championship.setAdmin_password(championshipDTO.admin_password());
        championship.setCarGroup(championshipDTO.carGroup());
        championship.setTrackMedalsRequirement(championshipDTO.trackMedalsRequirement());
        championship.setSafetyRatingRequirement(championshipDTO.safetyRatingRequirement());
        championship.setRacecraftRatingRequirement(championshipDTO.racecraftRatingRequirement());
        championship.setPassword(championshipDTO.password());
        championship.setSpectatorPassword(championshipDTO.spectatorPassword());
        championship.setMaxCarSlots(championshipDTO.maxCarSlots());
        championship.setDumpLeaderboards(championshipDTO.dumpLeaderboards());
        championship.setIsRaceLocked(championshipDTO.isRaceLocked());
        championship.setImageContent(championshipDTO.imageContent());
        return championship;
    }
}
