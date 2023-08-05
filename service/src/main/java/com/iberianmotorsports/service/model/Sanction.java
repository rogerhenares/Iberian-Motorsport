package com.iberianmotorsports.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

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
    @Column(name = "time")
    private Long time;

    @NotNull
    @Column(name = "lap")
    private Integer lap;

    @NotNull
    @Column(name = "sector")
    @Range(min=1, max=3)
    private Integer sector;

    @NotNull
    @Column(name = "penalty")
    private Long penalty;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "race_id", referencedColumnName = "race_id"),
            @JoinColumn(name = "grid_id", referencedColumnName = "grid_id")
    })
    private GridRace gridRace;
}
