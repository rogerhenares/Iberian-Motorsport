package com.iberianmotorsports.service.repository;

import com.iberianmotorsports.service.model.Bop;
import com.iberianmotorsports.service.model.GridRace;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.composeKey.BopPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BopRepository extends JpaRepository<Bop, BopPrimaryKey> {

    List<Bop> findBopByBopPrimaryKey_Race(Race race);

}
