package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.ChampionshipDTO;
import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.ChampionshipCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ChampionshipMapper implements Function<ChampionshipDTO, Championship> {

    @Autowired
    RaceMapper raceMapper;

    @Autowired
    ChampionshipCategoryMapper championshipCategoryMapper;

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
        if (championshipDTO.imageContent() != null) {
        championship.setImageContent(championshipDTO.imageContent()); }
        championship.setStartDate(championshipDTO.startDate());
        championship.setStyle(championshipDTO.style().toUpperCase());
        championship.setRaceList(championshipDTO.raceList().stream().map(raceMapper).toList());
        championship.setDisabled(championshipDTO.disabled());
        championship.setStarted(championshipDTO.started());
        championship.setFinished(championshipDTO.finished());
        championship.setCategoryList(championshipDTO.championshipCategoryList()
                .stream().map(championshipCategoryMapper).toList());
        return championship;
    }
}
