package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.CarDTO;
import com.iberianmotorsports.service.controller.DTO.GridDTO;
import com.iberianmotorsports.service.model.Car;
import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.repository.CarRepository;
import com.iberianmotorsports.service.service.CarService;
import com.iberianmotorsports.service.service.ChampionshipService;
import com.iberianmotorsports.service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        return grid;
    }
}

