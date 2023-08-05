package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.GridRace;

import java.util.List;

public interface GridRaceService {

    GridRace getGridRace(Long gridId, Long raceId);

    List<GridRace> getGridRaceForRace(Long raceId);

    void calculateGridRace(Long raceId);

}
