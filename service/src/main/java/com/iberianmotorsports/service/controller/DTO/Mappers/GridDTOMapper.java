package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.GridDTO;
import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class GridDTOMapper implements Function<Grid, GridDTO> {

    ChampionshipDTOMapper championshipDTOMapper;
    UserDTOMapper userDTOMapper;
    CarDTOMapper carDTOMapper;

    @Override
    public GridDTO apply(Grid grid) {
        List<Long> driverSteamIds = Collections.emptyList();
        if (grid.getDrivers() != null) {
            driverSteamIds = grid.getDrivers().stream().map(User::getSteamId).toList();
        }
        return new GridDTO(
                grid.getId(),
                grid.getCarNumber(),
                grid.getCarLicense(),
                grid.getCar().getId(),
                grid.getChampionship().getId(),
                driverSteamIds
        );
    }
}
