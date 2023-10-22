package com.iberianmotorsports.service.controller.DTO;

public record BopDTO(
        Long raceId,
        CarDTO car,
        Integer ballastKg,
        Integer restrictor
) {
}
