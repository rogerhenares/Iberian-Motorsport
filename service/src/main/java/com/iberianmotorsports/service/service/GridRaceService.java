package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.model.GridRace;
import com.iberianmotorsports.service.model.Race;

import java.util.List;

public interface GridRaceService {

    GridRace getGridRace(Long gridId, Long raceId);

    List<GridRace> getGridRaceForRace(Long raceId);

    void calculateDropRoundForGrid(Grid grid);

    void calculateDropRoundForRaceChampionship(Race race);

    void calculateGridRace(Long raceId);

    GridRace saveGridRace(GridRace gridRace);

    void calculatePointsToGridRace(GridRace gridRace, int position, GridRace winner);

}
