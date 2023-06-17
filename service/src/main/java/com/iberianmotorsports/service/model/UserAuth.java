package com.iberianmotorsports.service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="USER_AUTH")
public class UserAuth {

    @Id
    @Column(name = "steam_id")
    private Long steamId;

    @Column(name = "token")
    private String token;

    @Column(name = "last_login")
    private Long lastLogin;
}
