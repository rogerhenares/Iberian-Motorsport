package com.iberianmotorsports.service.model.parsing.imports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Penalty {

    private Float carId;
    private Float driverIndex;
    private String reason;
    private String penalty;
    private Float penaltyValue;
    private Float violationInLap;
    private Float clearedInLap;

}
