package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.SessionDTOMapper;
import com.iberianmotorsports.service.controller.DTO.RaceDTO;
import com.iberianmotorsports.service.controller.DTO.RaceRulesDTO;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.RaceRules;
import com.iberianmotorsports.service.model.Session;
import com.iberianmotorsports.service.repository.RaceRepository;
import com.iberianmotorsports.service.service.ChampionshipService;
import com.iberianmotorsports.service.service.RaceRulesService;
import com.iberianmotorsports.service.service.RaceService;
import com.iberianmotorsports.service.service.SessionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;


@AllArgsConstructor
@Transactional
@Service("RaceService")
public class RaceServiceImpl implements RaceService {

    private ChampionshipService championshipService;

    private RaceRepository raceRepository;

    private RaceMapper raceMapper;

    private SessionService sessionService;
    private RaceRulesService raceRulesService;

    @Override
    public Race saveRace(RaceDTO raceDTO) {
        Race race = raceMapper.apply(raceDTO);
        race.setId(null);
        Race createdRace = raceRepository.save(race);
        //TODO include statement to evaluate sessionDTO should contains every session for free practice/ qualy / race/
        raceDTO.sessionDTOList()
                .stream()
                .map(sessionDTO -> sessionService.saveSession(sessionDTO, createdRace))
                .toList();
        raceRulesService.saveRaceRules(raceDTO.raceRulesDTO(), createdRace);
        return raceRepository.save(race);
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
    public Race updateRace(Race race) {
        return raceRepository.save(race);
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

}
