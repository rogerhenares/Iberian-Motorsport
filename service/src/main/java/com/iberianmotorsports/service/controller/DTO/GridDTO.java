package com.iberianmotorsports.service.controller.DTO;

import java.util.List;

public record GridDTO(
        Long id,
        Integer carNumber,
        String teamName,
        String carLicense,
        Long championshipId,
        List<UserDTO> driversList,
        CarDTO car,
        Double points,
        Long licensePoints,
        Boolean disabled
) {
}
