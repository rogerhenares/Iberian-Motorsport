package com.iberianmotorsports.service.repository;

import com.iberianmotorsports.service.model.ChampionshipCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChampionshipCategoryRepository extends JpaRepository<ChampionshipCategory, Long> {
    List<ChampionshipCategory> findChampionshipCategoriesByChampionship_id(Long id);
}
