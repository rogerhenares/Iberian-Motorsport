package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.controller.DTO.RaceRulesDTO;
import com.iberianmotorsports.service.model.RaceRules;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface RaceRulesService {

    RaceRules saveRaceRules(RaceRulesDTO raceRulesDTO);

    RaceRules findRaceRulesById(Long id);


    Page<RaceRules> findAllRaceRules(Pageable pageable);

    RaceRules updateRaceRules(RaceRules raceRules);

    void deleteRaceRules(Long id);

    Boolean isAlreadyInDatabase(Long id);

    String exportRaceRules(RaceRules raceRules) throws IOException;
}
