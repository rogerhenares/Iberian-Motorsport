package com.iberianmotorsports.service.model.parsing.imports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Timing {

    private Integer lastLap;
    private List<Object> lastSplits;
    private Integer bestLap;
    private List<Float> bestSplits;
    private Integer totalTime;
    private Integer lapCount;
    private Float lastSplitId;

}
