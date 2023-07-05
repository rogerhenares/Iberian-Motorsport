package com.iberianmotorsports.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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

    @NotBlank
    @Column(name = "car_license")
    private String carLicense;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "championship_id")
    private Championship championship;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "GRID_USER",
            joinColumns = @JoinColumn(name = "grid_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> drivers;
}
