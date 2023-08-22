package com.iberianmotorsports.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "GRID")
public class Grid implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "car_number")
    private Integer carNumber;

    @NotNull
    @Column(name = "team_name")
    private String teamName;

    @NotBlank
    @Column(name = "car_license")
    private String carLicense;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "championship_id", nullable = false)
    private Championship championship;

    @Column(name = "disabled")
    private Boolean disabled;

    @ManyToMany
    @JoinTable(
            name = "GRID_USER",
            joinColumns = @JoinColumn(name = "grid_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> drivers;

    @OneToMany(mappedBy = "gridRacePrimaryKey.grid", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    private List<GridRace> gridRaceList;

    @Transient
    private Double points;

}
