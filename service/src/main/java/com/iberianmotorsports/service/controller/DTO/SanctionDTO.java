package com.iberianmotorsports.service.controller.DTO;

public record SanctionDTO (
    Long id,
    Integer lap,
    String penalty,
    String reason,
    Long raceId,
    Long gridId,
    GridDTO grid,
    Boolean inGame,
    Long licensePoints
    ){
}
