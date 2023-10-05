package com.iberianmotorsports.service.model.parsing.export;

import com.iberianmotorsports.service.model.parsing.Driver;
import com.iberianmotorsports.service.model.parsing.properties.EntryProperties;
import lombok.Data;

import java.util.List;


@Data
public class Entry extends EntryProperties {

    private List<Driver> drivers;
    private Float raceNumber;
    private Float overrideDriverInfo;
    private Float isServerAdmin;

}
