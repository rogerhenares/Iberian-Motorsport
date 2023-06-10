package com.iberianmotorsports.service.controller.DTO;

public record UserDTO(
        Long steamId,
        String firstName,
        String lastName,
        String shortName,
        String nationality
) {
}
