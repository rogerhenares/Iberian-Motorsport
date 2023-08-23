package com.iberianmotorsports.service.repository;

import com.iberianmotorsports.service.model.Sanction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanctionRepository extends JpaRepository<Sanction, Long> {

    List<Sanction> findSanctionsByGridRace_GridRacePrimaryKey_Race_Id(Long raceId);

}
