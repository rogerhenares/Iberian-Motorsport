package com.iberianmotorsports.service.controller.DTO;

import java.util.List;

public record GridDTO(
        Long id,
        Integer carNumber,
        String carLicense,
        Long championshipId,
        CarDTO car,
        List<UserDTO> driversList,
        Double points
) {
}
