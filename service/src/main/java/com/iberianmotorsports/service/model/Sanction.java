package com.iberianmotorsports.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "SANCTION")
public class Sanction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "lap")
    private Integer lap;

    @NotNull
    @Column(name = "penalty")
    private String penalty;

    @Column(name = "reason")
    private String reason;

    @Column(name = "in_game")
    private Boolean inGame;

    @Column(name = "sanction_points")
    private Long sanctionPoints;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "race_id", referencedColumnName = "race_id"),
            @JoinColumn(name = "grid_id", referencedColumnName = "grid_id")
    })
    private GridRace gridRace;
}
