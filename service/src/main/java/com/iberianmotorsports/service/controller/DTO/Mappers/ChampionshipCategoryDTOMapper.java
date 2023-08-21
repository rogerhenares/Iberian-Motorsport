package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.ChampionshipCategoryDTO;
import com.iberianmotorsports.service.model.ChampionshipCategory;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ChampionshipCategoryDTOMapper implements Function<ChampionshipCategory, ChampionshipCategoryDTO> {

    @Override
    public ChampionshipCategoryDTO apply(ChampionshipCategory championshipCategory) {
        return new ChampionshipCategoryDTO(
                championshipCategory.getId(),
                championshipCategory.getCategory(),
                championshipCategory.getMax()
        );
    }
}
