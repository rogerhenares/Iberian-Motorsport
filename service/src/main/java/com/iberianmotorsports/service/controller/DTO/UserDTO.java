package com.iberianmotorsports.service.controller.DTO;

public record UserDTO(
        String steamId,
        String firstName,
        String lastName,
        String shortName,
        String nationality
) {
}
