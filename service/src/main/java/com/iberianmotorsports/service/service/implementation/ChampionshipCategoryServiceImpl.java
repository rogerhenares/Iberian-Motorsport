package com.iberianmotorsports.service.service.implementation;

import com.iberianmotorsports.service.model.ChampionshipCategory;
import com.iberianmotorsports.service.repository.ChampionshipCategoryRepository;
import com.iberianmotorsports.service.service.ChampionshipCategoryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Transactional
@Service("ChampionshipCategoryService")
public class ChampionshipCategoryServiceImpl implements ChampionshipCategoryService {

    private ChampionshipCategoryRepository championshipCategoryRepository;

    //TODO review
    @Override
    public ChampionshipCategory findCategoryByChampionshipId(Long id) {
        ChampionshipCategory category = championshipCategoryRepository.findChampionshipCategoriesByChampionship_id(id).get(0);
        return category;
    }

}
