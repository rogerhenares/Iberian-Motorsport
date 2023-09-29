package com.iberianmotorsports.service.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChampionshipStyleType {
    SOLO("SOLO"),
    TEAM("TEAM"),
    TEAM_SOLO("TEAM-SOLO");

    private final String value;

}
