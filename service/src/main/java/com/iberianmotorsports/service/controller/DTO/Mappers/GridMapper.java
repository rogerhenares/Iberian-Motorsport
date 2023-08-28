package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.GridDTO;
import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.Grid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GridMapper implements Function<GridDTO, Grid> {

    CarMapper carMapper;
    UserMapper userMapper;

    @Override
    public Grid apply(GridDTO gridDTO) {
        Grid grid = new Grid();
        Championship championship = new Championship();
        championship.setId(gridDTO.championshipId());
        grid.setCarLicense(gridDTO.carLicense());
        grid.setCarNumber(gridDTO.carNumber());
        grid.setChampionship(championship);
        grid.setTeamName(gridDTO.teamName());
        grid.setCar(carMapper.apply(gridDTO.car()));
        grid.setDrivers(gridDTO.driversList().stream().map(userMapper).toList());
        grid.setPoints(gridDTO.points());
        return grid;
    }
}

