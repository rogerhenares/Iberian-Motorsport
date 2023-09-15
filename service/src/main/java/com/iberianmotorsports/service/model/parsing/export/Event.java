package com.iberianmotorsports.service.model.parsing.export;

import com.iberianmotorsports.service.model.parsing.imports.Sessions;
import lombok.Data;

import java.util.List;

@Data
public class Event {
    private String track;
    private Float preRaceWaitingTimeSeconds;
    private Float sessionOverTimeSeconds;
    private Float ambientTemp;
    private Float cloudLevel;
    private Float rain;
    private Float weatherRandomness;
    private Float configVersion;
    private List<Sessions> sessions;
}
