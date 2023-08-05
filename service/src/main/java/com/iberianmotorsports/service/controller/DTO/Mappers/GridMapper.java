package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.CarDTO;
import com.iberianmotorsports.service.controller.DTO.GridDTO;
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
    ChampionshipMapper championshipMapper;
    UserMapper userMapper;

    @Override
    public Grid apply(GridDTO gridDTO) {
        Grid grid = new Grid();
        Car car = new Car();
        Championship championship = new Championship();
        // ... other code ...

        List<User> drivers = Collections.emptyList();
        if (gridDTO.driversList() != null) {
            drivers = gridDTO.driversList().stream()
                    .map(driverSteamId -> {
                        User user = new User();
                        user.setSteamId(driverSteamId);
                        return user;
                    })
                    .toList();
        }
        grid.setDrivers(drivers);
        return grid;
    }
}

