package com.iberianmotorsports.service.model.parsing.imports;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Lap {
    @JsonProperty("carId")
    private Float carId;

    @JsonProperty("driverIndex")
    private Float dirverIndex;

    @JsonProperty("laptime")
    private Float lapTime;

    @JsonProperty("isValidForBest")
    private Boolean isValidForBest;

    @JsonProperty("splits")
    private List<Float> splits;

}
