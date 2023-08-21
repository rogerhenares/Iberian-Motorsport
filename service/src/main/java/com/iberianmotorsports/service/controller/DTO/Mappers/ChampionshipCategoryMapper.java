package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.ChampionshipCategoryDTO;
import com.iberianmotorsports.service.model.ChampionshipCategory;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ChampionshipCategoryMapper implements Function<ChampionshipCategoryDTO, ChampionshipCategory> {

    @Override
    public ChampionshipCategory apply(ChampionshipCategoryDTO championshipCategoryDTO) {
        ChampionshipCategory championshipCategory = new ChampionshipCategory();
        championshipCategory.setId(championshipCategoryDTO.id());
        championshipCategory.setCategory(championshipCategoryDTO.category());
        championshipCategory.setMax(championshipCategoryDTO.max());
        return championshipCategory;
    }
}
