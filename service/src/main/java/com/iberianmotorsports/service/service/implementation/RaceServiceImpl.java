package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.repository.RaceRepository;
import com.iberianmotorsports.service.service.RaceService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import static com.iberianmotorsports.service.Utils.Utils.defaultPageable;

@AllArgsConstructor
@Transactional
@Service("RaceService")
public class RaceServiceImpl implements RaceService {

    @Autowired
    private RaceRepository raceRepository;

    @Override
    public Race saveRace(Race race) {
        if (isAlreadyInDatabase(race.getId()))
            throw new ServiceException(ErrorMessages.DUPLICATED_RACE.getDescription());
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
    public Page<Race> findAllRaces() {
        return raceRepository.findAll(defaultPageable);
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
