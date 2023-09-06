package com.iberianmotorsports.service.model.parsing;

import com.iberianmotorsports.service.model.Session;
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
