package com.iberianmotorsports.service.controller.DTO;

import java.util.List;

public record UserDTO(
        Long userId,
        String steamId,
        String firstName,
        String shortName,
        String lastName,
        String nationality,
        List<String> roleList
) {
}
