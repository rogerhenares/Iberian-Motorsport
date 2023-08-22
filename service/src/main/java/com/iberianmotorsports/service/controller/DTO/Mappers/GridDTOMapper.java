package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.CarDTO;
import com.iberianmotorsports.service.controller.DTO.GridDTO;
import com.iberianmotorsports.service.controller.DTO.UserDTO;
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

    UserDTOMapper userDTOMapper;
    CarDTOMapper carDTOMapper;

    @Override
    public GridDTO apply(Grid grid) {
        return new GridDTO(
                grid.getId(),
                grid.getCarNumber(),
                grid.getCarLicense(),
                grid.getTeamName(),
                grid.getChampionship().getId(),
                carDTOMapper.apply(grid.getCar()),
                grid.getDrivers().stream().map(userDTOMapper).toList(),
                grid.getPoints()
        );
    }
}
