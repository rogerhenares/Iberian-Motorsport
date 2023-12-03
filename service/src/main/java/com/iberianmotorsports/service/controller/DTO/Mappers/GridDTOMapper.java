package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.GridDTO;
import com.iberianmotorsports.service.model.Grid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
                grid.getTeamName(),
                grid.getCarLicense(),
                grid.getChampionship().getId(),
                grid.getDrivers().stream().map(userDTOMapper).toList(),
                grid.getManagerId(),
                carDTOMapper.apply(grid.getCar()),
                grid.getPoints(),
                grid.getPointsDrop(),
                grid.getLicensePoints(),
                grid.getPassword(),
                grid.getDisabled(),
                grid.getNewManagerId()
        );
    }
}
