package com.iberianmotorsports.service.controller.DTO;

import com.iberianmotorsports.service.model.Car;

import java.util.List;

public record GridDTO(
        Long id,
        Integer carNumber,
        String carLicense,
        CarDTO carDTO,
        ChampionshipDTO championshipDTO,
        List<UserDTO> driversList
) {
}
