package com.iberianmotorsports.service.model.parsing.imports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lap {
    @JsonProperty("carId")
    private Integer carId;

    @JsonProperty("driverIndex")
    private Float dirverIndex;

    @JsonProperty("laptime")
    private Integer lapTime;

    @JsonProperty("isValidForBest")
    private Boolean isValidForBest;

    @JsonProperty("splits")
    private List<Float> splits;

}
