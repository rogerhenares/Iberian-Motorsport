package com.iberianmotorsports.service.model.parsing.imports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Timing {

    private Float lastLap;
    private List<Object> lastSplits;
    private Float bestLap;
    private List<Float> bestSplits;
    private Float totalTime;
    private Float lapCount;
    private Float lastSplitId;

}
