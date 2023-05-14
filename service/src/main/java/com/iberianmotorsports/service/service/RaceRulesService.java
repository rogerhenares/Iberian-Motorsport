package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.RaceRules;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface RaceRulesService {

    RaceRules saveRaceRules(RaceRules raceRules);

    RaceRules findRaceRulesById(Long id);


    Page<RaceRules> findAllRaceRules();

    RaceRules updateRaceRules(RaceRules raceRules);

    void deleteRaceRules(Long id);

    Boolean isAlreadyInDatabase(Long id);

    String exportRaceRules(RaceRules raceRules) throws IOException;
}
