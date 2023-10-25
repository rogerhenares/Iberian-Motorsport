package com.iberianmotorsports.service.model.parsing.export;

import com.iberianmotorsports.service.model.parsing.Driver;
import com.iberianmotorsports.service.model.parsing.properties.EntryProperties;
import lombok.Data;

import java.util.List;


@Data
public class Entry extends EntryProperties {

    private List<Driver> drivers;
    private Integer raceNumber;
    private Integer forcedCarModel;
    private Float overrideDriverInfo;
    private Integer defaultGridPosition;
    private Integer ballastKg;
    private Integer restrictor;
    private String customCar;
    private Integer overrideCarModelForCustomCar;
    private Float isServerAdmin;

}
