package com.iberianmotorsports.service.model.parsing;

import lombok.Data;

@Data
public class Penalty {

    private Float carId;
    private Float driverIndex;
    private String reason;
    private String penalty;
    private Float penaltyValue;
    private Float violationInLap;
    private Float clearedInLap;

}
