package com.iberianmotorsports.service.controller.DTO;

import java.util.List;

public record UserDTO(
        String steamId,
        String firstName,
        String shortName,
        String lastName,
        String nationality,
        List<String> roleList
) {
}
