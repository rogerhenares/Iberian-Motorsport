package com.iberianmotorsports.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Entity
@Data
@Table(name = "RACE")
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "championship_id", nullable = false)
    private Championship championship;

    @NotEmpty
    @Column(name = "track")
    private String track;

    @NotNull
    @Range(min=30)
    @Column(name = "pre_race_waiting_time_seconds")
    private Integer preRaceWaitingTimeSeconds;

    @NotNull
    @Column(name = "session_over_time_seconds")
    private Integer sessionOverTimeSeconds;

    @NotNull
    @Column(name = "ambient_temp")
    private Integer ambientTemp;

    @NotNull
    @Range(min=0, max=1)
    @Column(name = "cloud_level")
    private Float cloudLevel;

    @NotNull
    @Range(min=0, max=1)
    @Column(name = "rain")
    private Float rain;

    @NotNull
    @Range(min=0, max=7)
    @Column(name = "weather_randomness")
    private Integer weatherRandomness;

    @NotNull
    @Range(min=1)
    @Column(name = "post_qualy_seconds")
    private Integer postQualySeconds;

    @NotNull
    @Column(name = "post_race_seconds")
    private Integer postRaceSeconds;

    @NotEmpty
    @Column(name = "server_name")
    private String serverName;

    @OneToOne(mappedBy = "race", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private RaceRules raceRules;

    @OneToMany(mappedBy = "race", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Session> sessionList;

    @Transient
    private Long championshipId;
}
