package com.iberianmotorsports.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Entity
@Data
@Table(name="USER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    @Column(name = "steam_id")
    @JsonProperty("steamid")
    private Long steamId;

    @NotBlank
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
}
