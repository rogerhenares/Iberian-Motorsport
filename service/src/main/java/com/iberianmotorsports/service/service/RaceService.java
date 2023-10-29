package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.controller.DTO.RaceDTO;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.utils.RaceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface RaceService {

    Race saveRace(RaceDTO raceDTO);

    Race findRaceById(Long id);

    Race findRaceByName(String name);

    Page<Race> findAllRaces(Pageable pageRequest);

    Race updateRace(RaceDTO RaceDTO);

    void deleteRace(Long id);

    Boolean isAlreadyInDatabase(Long id);

    String exportRace(Race race) throws IOException;

    void setRaceStatus(Race race, RaceStatus raceStatus);

    List<Race> getRaceByStatusAndDate(RaceStatus raceStatus, LocalDateTime currentTime);
}
