package com.iberianmotorsports.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;


@Entity
@Data
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "nationality")
    private String nationality;

//    @Column(name = "open_ids")
//    private Set<UserOpenIds> userOpenIdsSet = new HashSet<>();

}