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
        championship.setAdminPassword(championshipDTO.adminPassword());
        championship.setCarGroup(championshipDTO.carGroup());
        championship.setTrackMedalsRequirement(championshipDTO.trackMedalsRequirement());
        championship.setSafetyRatingRequirement(championshipDTO.safetyRatingRequirement());
        championship.setRacecraftRatingRequirement(championshipDTO.racecraftRatingRequirement());
        championship.setPassword(championshipDTO.password());
        championship.setSpectatorPassword(championshipDTO.spectatorPassword());
        championship.setMaxCarSlots(championshipDTO.maxCarSlots());
        championship.setDumpLeaderboards(championshipDTO.dumpLeaderboards());
        championship.setIsRaceLocked(championshipDTO.isRaceLocked());
        championship.setRandomizeTrackWhenEmpty(championshipDTO.randomizeTrackWhenEmpty());
        championship.setCentralEntryListPath(championshipDTO.centralEntryListPath());
        championship.setAllowAutoDq(championshipDTO.allowAutoDq());
        championship.setShortFormationLap(championshipDTO.shortFormationLap());
        championship.setDumpEntryList(championshipDTO.dumpEntryList());
        championship.setFormationLapType(championshipDTO.formationLapType());
        championship.setIgnorePrematureDisconnects(championshipDTO.ignorePrematureDisconnects());
        championship.setImageContent(championshipDTO.imageContent());
        championship.setStartDate(championshipDTO.startDate());
        return championship;
    }
}
