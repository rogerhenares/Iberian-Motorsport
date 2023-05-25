package com.iberianmotorsports.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column(name = "steam_id")
    @JsonProperty("steamid")
    private Long steamId;

    @Column(name = "first_name")
    @JsonProperty("personaname")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "nationality")
    @JsonProperty("loccountrycode")
    private String nationality;

//    @Column(name = "open_ids")
//    private Set<UserOpenIds> userOpenIdsSet = new HashSet<>();

}