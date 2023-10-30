package com.iberianmotorsports.service.model.parsing.export;

import com.iberianmotorsports.service.model.parsing.imports.Sessions;
import lombok.Data;

import java.util.List;

@Data
public class Event {
    private String track;
    private Integer preRaceWaitingTimeSeconds;
    private Integer sessionOverTimeSeconds;
    private Integer ambientTemp;
    private Float cloudLevel;
    private Float rain;
    private Integer weatherRandomness;
    private List<Sessions> sessions;
}
