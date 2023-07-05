package com.iberianmotorsports.service.controller.DTO;

public record CarDTO(
        Long id,
        String manufacturer,
        String model,
        String category
) {
}
