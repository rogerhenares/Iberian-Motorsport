package com.iberianmotorsports.service.model.parsing;

import lombok.Data;

@Data
public class Results {

    private String sessionType;
    private String trackName;
    private Float sessionIndex;
    private Float raceWeekendIndex;
    private String metaData;
    private String serverName;
    private SessionResult sessionResult;

}
