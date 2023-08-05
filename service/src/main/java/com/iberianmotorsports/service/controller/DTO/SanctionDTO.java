package com.iberianmotorsports.service.controller.DTO;

public record SanctionDTO (
    Long time,
    Integer lap,
    Integer sector,
    Long penalty,
    String description,
    Long raceId,
    Long gridId
    ){
}
