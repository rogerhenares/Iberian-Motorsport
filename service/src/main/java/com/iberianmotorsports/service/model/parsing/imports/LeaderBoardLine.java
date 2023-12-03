package com.iberianmotorsports.service.model.parsing.imports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iberianmotorsports.service.model.parsing.Driver;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaderBoardLine {
    @JsonProperty("car")
    private Car car;
    @JsonProperty("currentDriver")
    private Driver currentDriver;
    @JsonProperty("currentDriverIndex")
    private Float currentDriverIndex;
    @JsonProperty("timing")
    private Timing timing;
    @JsonProperty("missingMandatoryPitstop")
    private Float missingMandatoryPitstop;
    @JsonProperty("driverTotalTimes")
    private List<?> driverTotalTimes;
}
