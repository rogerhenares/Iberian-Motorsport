package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceRulesMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.SessionMapper;
import com.iberianmotorsports.service.controller.DTO.RaceDTO;
import com.iberianmotorsports.service.controller.DTO.SessionDTO;
import com.iberianmotorsports.service.model.Bop;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.RaceRules;
import com.iberianmotorsports.service.model.Session;
import com.iberianmotorsports.service.repository.RaceRepository;
import com.iberianmotorsports.service.service.*;
import com.iberianmotorsports.service.utils.RaceStatus;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
@Transactional
@Service("RaceService")
public class RaceServiceImpl implements RaceService {

    private static final Set<String> SESSION_TYPE_MANDATORY = Set.of("Q", "R");

    private ChampionshipService championshipService;

    private RaceRepository raceRepository;
    private CarService carService;

    private RaceMapper raceMapper;

    private SessionService sessionService;

    private RaceRulesService raceRulesService;

    private SessionMapper sessionMapper;

    private RaceRulesMapper raceRulesMapper;

    @Override
    public Race saveRace(RaceDTO raceDTO) {
        if(raceDTO.sessionDTOList() != null) {
            if(!validateSessionForRace(raceDTO.sessionDTOList()))
                throw new ServiceException(ErrorMessages.RACE_SESSION_TYPE_MISSING.getDescription());
        }
        if (raceDTO.id() != -1) {
            return updateRace(raceDTO);
        }
        Race race = raceMapper.apply(raceDTO);
        race.setChampionship(championshipService.findChampionshipById(race.getChampionshipId()));
        race.setBopList(new ArrayList<>());
        Race savedRace = raceRepository.save(race);
        savedRace.getBopList().addAll(raceMapper.apply(raceDTO).getBopList().stream()
            .map(bop -> {
                bop.getBopPrimaryKey().setRace(savedRace);
                return bop;
            }).toList());
        return raceRepository.save(savedRace);
    }

    private Boolean validateSessionForRace(List<SessionDTO> sessionDTOList) {
        Set<String> sessionTypeList = sessionDTOList.stream()
                .map(SessionDTO::sessionType)
                .map(String::trim)
                .collect(Collectors.toSet());
        return sessionTypeList.containsAll(SESSION_TYPE_MANDATORY);
    }

    @Override
    public Race findRaceById(Long id) {
        Optional<Race> raceOptional = raceRepository.findById(id);
        if(raceOptional.isEmpty()) throw new ServiceException(ErrorMessages.RACE_NOT_IN_DB.getDescription());
        return raceOptional.orElse(null);
    }

    @Override
    public Race findRaceByName(String name) {
        Optional<Race> raceOptional = raceRepository.findByTrack(name);
        if(raceOptional.isEmpty()) throw new ServiceException(ErrorMessages.RACE_NOT_IN_DB.getDescription());
        return raceOptional.orElse(null);
    }

    @Override
    public Page<Race> findAllRaces(Pageable pageRequest) {
        return raceRepository.findAll(pageRequest);
    }

    @Override
    public Race updateRace(RaceDTO raceDTO) {

        Race race = raceMapper.apply(raceDTO);
        Race raceToUpdate = findRaceById(race.getId());

        // Update Race properties
        raceToUpdate.setTrack(race.getTrack());
        raceToUpdate.setPreRaceWaitingTimeSeconds(race.getPreRaceWaitingTimeSeconds());
        raceToUpdate.setSessionOverTimeSeconds(race.getSessionOverTimeSeconds());
        raceToUpdate.setAmbientTemp(race.getAmbientTemp());
        raceToUpdate.setCloudLevel(race.getCloudLevel());
        raceToUpdate.setRain(race.getRain());
        raceToUpdate.setWeatherRandomness(race.getWeatherRandomness());
        raceToUpdate.setPostQualySeconds(race.getPostQualySeconds());
        raceToUpdate.setPostRaceSeconds(race.getPostRaceSeconds());
        raceToUpdate.setServerName(race.getServerName());
        raceToUpdate.setStartDate(race.getStartDate());
        raceToUpdate.setChampionship(championshipService.findChampionshipById(race.getChampionshipId()));

        // Update RaceRules
        RaceRules raceRulesToUpdate = raceToUpdate.getRaceRules();
        RaceRules raceRules = race.getRaceRules();
        raceRulesToUpdate.setQualifyStandingType(raceRules.getQualifyStandingType());
        raceRulesToUpdate.setPitWindowLengthSec(raceRules.getPitWindowLengthSec());
        raceRulesToUpdate.setDriverStintTimeSec(raceRules.getDriverStintTimeSec());
        raceRulesToUpdate.setMandatoryPitstopCount(raceRules.getMandatoryPitstopCount());
        raceRulesToUpdate.setMaxTotalDrivingTime(raceRules.getMaxTotalDrivingTime());
        raceRulesToUpdate.setMaxDriversCount(raceRules.getMaxDriversCount());
        raceRulesToUpdate.setIsRefuellingAllowedInRace(raceRules.getIsRefuellingAllowedInRace());
        raceRulesToUpdate.setIsRefuellingTimeFixed(raceRules.getIsRefuellingTimeFixed());
        raceRulesToUpdate.setIsMandatoryPitstopRefuellingRequired(raceRules.getIsMandatoryPitstopRefuellingRequired());
        raceRulesToUpdate.setIsMandatoryPitstopTyreChangeRequired(raceRules.getIsMandatoryPitstopTyreChangeRequired());
        raceRulesToUpdate.setIsMandatoryPitstopSwapDriverRequired(raceRules.getIsMandatoryPitstopSwapDriverRequired());
        raceRulesToUpdate.setTyreSetCount(raceRules.getTyreSetCount());

        // Update Session
        List<Session> sessionListToUpdate = raceToUpdate.getSessionList();
        List<Session> sessionList = race.getSessionList();
        for (int i = 0; i < sessionList.size(); i++) {
            Session sessionToUpdate = sessionListToUpdate.get(i);
            Session session = sessionList.get(i);
            sessionToUpdate.setHourOfDay(session.getHourOfDay());
            sessionToUpdate.setDayOfWeekend(session.getDayOfWeekend());
            sessionToUpdate.setTimeMultiplier(session.getTimeMultiplier());
            sessionToUpdate.setSessionType(session.getSessionType());
            sessionToUpdate.setSessionDurationMinutes(session.getSessionDurationMinutes());
        }

        raceToUpdate.getBopList().clear();
        for(Bop bop: race.getBopList()){
            bop.getBopPrimaryKey().setRace(raceToUpdate);
            raceToUpdate.getBopList().add(bop);
        }

        return raceRepository.save(raceToUpdate);
    }


    @Override
    public void deleteRace(Long id) {
        raceRepository.deleteById(id);
    }

    @Override
    public Boolean isAlreadyInDatabase(Long id) {
        if (id == null) {
            return false;
        }
        Optional<Race> raceOptional = raceRepository.findById(id);
        return raceOptional.isPresent();
    }

    public Boolean isInDatabase(String name) {
        return null;
    }

    @Override
    public String exportRace(Race race) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(race);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath() + "/event.json";
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(json);
            fileWriter.close();
            return "Race saved to " + filePath;
        } else {
            return "No directory selected";
        }
    }

    @Override
    public void setRaceStatus(Race race, RaceStatus raceStatus) {
        race.setStatus(raceStatus.name());
        raceRepository.save(race);
    }

    @Override
    public List<Race> getRaceByStatusAndDate(RaceStatus raceStatus, LocalDateTime currentTime) {
        List<Race> raceList = raceRepository.findAllByStatusIsAndStartDateBefore(raceStatus.name(), currentTime);
        raceList = raceList.stream().map(race -> {
            race.getChampionship().getGridList().size();
            race.getChampionship().getGridList().stream().map(grid -> {
                grid.getDrivers().size();
                return grid;
            });
            race.getBopList().size();
            race.getSessionList().size();
            return race;
        }).toList();
        return raceList;
    }
}
