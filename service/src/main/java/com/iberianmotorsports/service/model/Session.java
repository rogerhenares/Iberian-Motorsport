package com.iberianmotorsports.service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "\"SESSION\"")
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id", referencedColumnName = "id")
    private Race race;

}
