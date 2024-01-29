package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.GridDTO;
import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.service.implementation.UserServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Object steamId = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isUserInGrid = false;
        if(!steamId.equals("anonymousUser")){
            Long loggedUserSteamId = (Long) steamId;
            isUserInGrid = grid.getDrivers().stream().anyMatch(user -> user.getSteamId().equals(loggedUserSteamId));
            if(UserServiceImpl.isLoggedUserAdmin() || UserServiceImpl.isLoggedUserSteward()) {
                isUserInGrid = true;
            }
        }
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
                isUserInGrid ? grid.getPassword() : "",
                grid.getDisabled(),
                grid.getNewManagerId()
        );
    }
}
