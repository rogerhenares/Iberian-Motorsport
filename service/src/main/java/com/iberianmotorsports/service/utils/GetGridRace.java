package com.iberianmotorsports.service.utils;

import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.model.GridRace;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.composeKey.GridRacePrimaryKey;
import org.springframework.stereotype.Service;


public class GetGridRace {

    private GetGridRace() {

    }

    public static GridRace getGridRace(Long gridId, Long raceId) {
        Grid grid = new Grid();
        grid.setId(gridId);
        Race race = new Race();
        race.setId(raceId);
        GridRacePrimaryKey gridRacePrimaryKey = new GridRacePrimaryKey(grid, race);
        GridRace gridRace = new GridRace();
        gridRace.setGridRacePrimaryKey(gridRacePrimaryKey);
        return gridRace;
    }

}
