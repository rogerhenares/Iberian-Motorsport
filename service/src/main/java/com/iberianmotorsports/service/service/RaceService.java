package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.Race;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface RaceService {

    Race saveRace(Race race);

    Race findRaceById(Long id);

    Race findRaceByName(String name);

    Page<Race> findAllRaces();

    Race updateRace(Race Race);

    void deleteRace(Long id);

    Boolean isAlreadyInDatabase(Long id);

}
