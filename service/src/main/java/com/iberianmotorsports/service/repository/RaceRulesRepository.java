package com.iberianmotorsports.service.repository;

import com.iberianmotorsports.service.model.RaceRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRulesRepository extends JpaRepository<RaceRules, Long> {

}
