package com.iberianmotorsports.service.model.parsing.imports;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Results {

    @JsonProperty("sessionType")
    private String sessionType;

    @JsonProperty("trackName")
    private String trackName;

    @JsonProperty("sessionIndex")
    private Float sessionIndex;

    @JsonProperty("raceWeekendIndex")
    private Float raceWeekendIndex;

    @JsonProperty("metaData")
    private String metaData;

    @JsonProperty("serverName")
    private String serverName;

    @JsonProperty("sessionResult")
    private SessionResult sessionResult;

    @JsonProperty("laps")
    private List<Lap> laps;

    @JsonProperty("penalties")
    private List<Penalty> penalties;

    @JsonProperty("post_race_penalties")
    private List<Penalty> postRacePenalties;
}
