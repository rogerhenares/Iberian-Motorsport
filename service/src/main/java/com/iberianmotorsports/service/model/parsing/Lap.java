package com.iberianmotorsports.service.model.parsing;

import lombok.Data;

import java.util.List;

@Data
public class Lap {

    private Float carId;
    private Float dirverIndex;
    private Float lapTime;
    private Boolean isValidForBest;
    private List<Float> splits;

}
