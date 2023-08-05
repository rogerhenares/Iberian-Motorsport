package com.iberianmotorsports.service.repository;

import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.GridRace;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.composeKey.GridRacePrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GridRaceRepository extends JpaRepository<GridRace, GridRacePrimaryKey> {

    List<GridRace> findGridRacesByGridRacePrimaryKey_Race(Race race);

    List<GridRace> findGridRacesByGridRacePrimaryKey_Race_Championship(Championship championship);

}
