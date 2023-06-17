package com.iberianmotorsports.service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role")
    private String role;

}
