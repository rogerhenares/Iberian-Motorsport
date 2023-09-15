package com.iberianmotorsports.service.model.parsing.imports;

import com.iberianmotorsports.service.model.parsing.Driver;
import lombok.Data;

import java.util.List;

@Data
public class Car {

    private Float carId;
    private Float raceNumber;
    private Float carModel;
    private Float cupCategory;
    private String carGroup;
    private String teamName;
    private Float nationality;
    private Float carGuid;
    private Float teamGuid;
    private List<Driver> drivers;

}
