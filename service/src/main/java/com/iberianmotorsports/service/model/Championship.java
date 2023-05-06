package com.iberianmotorsports.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Championship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "description")
    private String description;

    @NotBlank
    @Column(name = "admin_password")
    private String admin_password;

    @NotBlank
    @Column(name = "car_group")
    private String carGroup;

    @NotNull
    @Range(min=0, max=3)
    @Column(name = "track_medals_requirement")
    private Integer trackMedalsRequirement;

    @NotNull
    @Range(min=-1, max=99)
    @Column(name = "safety_rating_requirement")
    private Integer safetyRatingRequirement;

    @NotNull
    @Range(min=-1, max=99)
    @Column(name = "racecraft_rating_requirement")
    private Integer racecraftRatingRequirement;

    @Column(name = "password")
    private String password;

    @Column(name = "spectator_password")
    private String spectatorPassword;

    @NotNull
    @Column(name = "max_car_slots")
    private Integer maxCarSlots;

    @NotNull
    @Column(name = "dump_leaderboards")
    private Integer dumpLeaderboards;

    @NotNull
    @Column(name = "is_race_locked")
    private Integer isRaceLocked;

    @OneToMany(mappedBy = "championship", fetch = FetchType.LAZY)
    private List<Race> raceList;

}