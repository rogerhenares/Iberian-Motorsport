package com.iberianmotorsports.service.model.parsing;

import lombok.Data;

import java.util.List;

@Data
public class Timing {

    private Float lastLap;
    private List<Object> lastSplits;
    private Float bestLap;
    private List<Float> bestSplits;
    private Float totalTime;
    private Float lapCount;
    private Float lastSplitId;

}
