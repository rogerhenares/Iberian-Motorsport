package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceRulesDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceRulesMapper;
import com.iberianmotorsports.service.controller.DTO.RaceRulesDTO;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.RaceRules;
import com.iberianmotorsports.service.repository.RaceRulesRepository;
import com.iberianmotorsports.service.service.RaceRulesService;
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
@Service("RaceRulesService")
public class RaceRulesServiceImpl implements RaceRulesService {

    @Autowired
    private RaceRulesRepository raceRulesRepository;

    private RaceRulesMapper raceRulesMapper;

    private RaceRulesDTOMapper raceRulesDTOMapper;

    @Override
    public RaceRules saveRaceRules(RaceRulesDTO raceRulesDTO, Race race) {
        RaceRules raceRules = raceRulesMapper.apply(raceRulesDTO);
        if (isAlreadyInDatabase(raceRules.getId()))
            throw new ServiceException(ErrorMessages.DUPLICATED_RACE_RULES.getDescription());
        raceRules.setRace(race);
        return raceRulesRepository.save(raceRules);
    }

    @Override
    public RaceRules findRaceRulesById(Long id) {
        Optional<RaceRules> raceRulesOptional = raceRulesRepository.findById(id);
        if(raceRulesOptional.isEmpty()) throw new ServiceException(ErrorMessages.RACE_RULES_NOT_IN_DB.getDescription());
        return raceRulesOptional.orElse(null);
    }

    @Override
    public Page<RaceRules> findAllRaceRules(Pageable pageRequest) {
        return raceRulesRepository.findAll(pageRequest);
    }

    @Override
    public RaceRules updateRaceRules(RaceRules raceRules) {
        return raceRulesRepository.save(raceRules);
    }

    @Override
    public void deleteRaceRules(Long id) {
        raceRulesRepository.deleteById(id);
    }

    @Override
    public Boolean isAlreadyInDatabase(Long id) {
        Optional<RaceRules> raceRulesOptional = raceRulesRepository.findById(id);
        return raceRulesOptional.isPresent();
    }

    public Boolean isInDatabase(String name) {
        return null;
    }

    @Override
    public String exportRaceRules(RaceRules raceRules) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(raceRules);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath() + "/raceRules.json";
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(json);
            fileWriter.close();
            return "Race rules saved to " + filePath;
        } else {
            return "No directory selected";
        }
    }

}
