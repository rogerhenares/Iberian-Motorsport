package com.iberianmotorsports.service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hour_of_day")
    private Integer hourOfDay;

    @Column(name = "day_of_weekend")
    private Integer dayOfWeekend;

    @Column(name = "time_multiplier")
    private Integer timeMultiplier;

    @Column(name = "session_type")
    private String sessionType;

    @Column(name = "session_duration_minutes")
    private Integer sessionDurationMinutes;

    // EL race_id es un one to one o un one to many?

}
