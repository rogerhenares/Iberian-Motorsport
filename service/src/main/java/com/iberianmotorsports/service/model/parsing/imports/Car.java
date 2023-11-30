package com.iberianmotorsports.service.model.parsing.imports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iberianmotorsports.service.model.parsing.Driver;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Car {

    private Integer carId;
    private Integer raceNumber;
    private Integer carModel;
    private Float cupCategory;
    private String carGroup;
    private String teamName;
    private Float nationality;
    private Integer carGuid;
    private Float teamGuid;
    private List<Driver> drivers;

}
