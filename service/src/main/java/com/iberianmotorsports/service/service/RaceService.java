package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface RaceService {

    Race saveRace(Race race);

    Race findRaceById(Long id);

    Race findRaceByName(String name);

    Page<Race> findAllRaces(Pageable pageRequest);

    Race updateRace(Race Race);

    void deleteRace(Long id);

    Boolean isAlreadyInDatabase(Long id);

    String exportRace(Race race) throws IOException;

}
