package com.iberianmotorsports.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "CHAMPIONSHIP")
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
    private String adminPassword;

    @NotBlank
    @Column(name = "car_group")
    private String carGroup;

    @Column(name = "sub_car_category")
    private String subCarGroup;

    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "finished")
    private Boolean finished;

    @Column(name = "started")
    private Boolean started;

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

    @Column(name = "max_sub_car_slots")
    private Integer maxSubCarSlots;

    @NotNull
    @Column(name = "dump_leaderboards")
    private Integer dumpLeaderboards;

    @NotNull
    @Column(name = "is_race_locked")
    private Integer isRaceLocked;

    @Column(name="randomize_track_when_empty")
    private Integer randomizeTrackWhenEmpty;

    @Column(name="central_entry_list_path")
    private String centralEntryListPath;

    @Column(name="allow_auto_dq")
    private Integer allowAutoDq;

    @Column(name="short_formation_lap")
    private Integer shortFormationLap;

    @Column(name="dump_entry_list")
    private Integer dumpEntryList;

    @Column(name="formation_lap_type")
    private Integer formationLapType;

    @Column(name="ignore_premature_disconnects")
    private Integer ignorePrematureDisconnects;

    @Column(name = "image_content")
    private String imageContent;


    @Column(name = "style")
    private String style;

    @OneToMany(mappedBy = "championship", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    private List<Race> raceList;

    @OneToMany(mappedBy = "championship", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Grid> gridList;

    @OneToMany(mappedBy = "championship", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<ChampionshipCategory> categoryList;

    @Transient
    private List<Car> carListForChampionship;

    @Transient
    private Boolean isLoggedUserInChampionship;
}
