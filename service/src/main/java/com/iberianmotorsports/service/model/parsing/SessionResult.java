package com.iberianmotorsports.service.model.parsing;

import lombok.Data;

import java.util.List;

@Data
public class SessionResult {

    private Float bestLap;
    private List<Float> bestSplits;
    private Float isWetSession;
    private Float type;
    private List<LeaderBoardLine> leaderBoardLines;
    private List<Lap> laps;
    private List<Penalty> penalties;
    private List<Penalty> postRacePenalties;
}
