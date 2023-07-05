package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.CarDTO;
import com.iberianmotorsports.service.controller.DTO.GridDTO;
import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GridMapper implements Function<GridDTO, Grid> {

    CarMapper carMapper;
    ChampionshipMapper championshipMapper;
    UserMapper userMapper;

    @Override
    public Grid apply(GridDTO gridDTO) {
        Grid grid = new Grid();
        grid.setId(gridDTO.id());
        grid.setCarNumber(gridDTO.carNumber());
        grid.setCarLicense(gridDTO.carLicense());
        grid.setCar(carMapper.apply(gridDTO.carDTO()));
        grid.setChampionship(championshipMapper.apply(gridDTO.championshipDTO()));
        grid.setDrivers(gridDTO.driversList().stream().map(userMapper).toList());
        return grid;
    }

}
