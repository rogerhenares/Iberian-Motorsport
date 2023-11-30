package com.iberianmotorsports.service.controller.DTO;

import java.util.List;

public record GridDTO(
        Long id,
        Integer carNumber,
        String teamName,
        String carLicense,
        Long championshipId,
        List<UserDTO> driversList,
        Long managerId,
        CarDTO car,
        Double points,
        Float licensePoints,
        String password,
        Boolean disabled,
        Long newManagerId
) {
}
