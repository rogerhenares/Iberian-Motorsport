package com.iberianmotorsports.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "CAR")
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "manufacturer")
    private String manufacturer;

    @NotBlank
    @Column(name = "model")
    private String model;

    @NotBlank
    @Column(name = "category")
    private String category;

    @NotBlank
    @Column(name = "model_id")
    private Integer modelId;
}
