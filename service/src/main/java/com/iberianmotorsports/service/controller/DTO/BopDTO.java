package com.iberianmotorsports.service.controller.DTO;

import com.iberianmotorsports.service.model.Car;

public record BopDTO(
        Long raceId,
        Car car,
        Integer ballastKg,
        Integer restrictor
) {
}
