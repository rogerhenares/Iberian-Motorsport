package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.model.GridRace;

import java.util.List;

public interface GridRaceService {

    GridRace getGridRace(Long gridId, Long raceId);

    List<GridRace> getGridRaceForRace(Long raceId);

    void calculateDropRoundForGrid(Grid grid);

    void calculateGridRace(Long raceId);

    GridRace saveGridRace(GridRace gridRace);

}
