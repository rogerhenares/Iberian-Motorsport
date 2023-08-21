package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.ChampionshipCategory;
import org.springframework.stereotype.Service;

@Service
public interface ChampionshipCategoryService {

    ChampionshipCategory findCategoryByChampionshipId(Long id);

}
