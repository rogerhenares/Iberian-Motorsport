package com.iberianmotorsports.service.repository;

import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.ChampionshipCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionshipCategoryRepository extends JpaRepository<ChampionshipCategory, Long> {
    ChampionshipCategory findCategoryByChampionship_Id(Long id);
}
