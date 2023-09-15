package com.iberianmotorsports.service.model.parsing.imports;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SessionResult {

    @JsonProperty("bestlap")
    private Float bestLap;

    @JsonProperty("bestSplits")
    private List<Float> bestSplits;

    @JsonProperty("isWetSession")
    private Float isWetSession;

    @JsonProperty("type")
    private Float type;

    @JsonProperty("leaderBoardLines")
    private List<LeaderBoardLine> leaderBoardLines;

}
