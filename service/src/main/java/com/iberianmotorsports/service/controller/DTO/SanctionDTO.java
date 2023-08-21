package com.iberianmotorsports.service.controller.DTO;

public record SanctionDTO (
    Long id,
    Long time,
    Integer lap,
    Integer sector,
    Long penalty,
    String description,
    Long gridId,
    Long raceId
    ){
}
