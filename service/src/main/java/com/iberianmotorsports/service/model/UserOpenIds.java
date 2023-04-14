package com.iberianmotorsports.service.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "USER_OPENID")
public class UserOpenIds implements Serializable {

    private String openIdUrl;

    private User user;

    public UserOpenIds() {
    }

    public UserOpenIds(String openIdUrl) {
        this.openIdUrl = openIdUrl;
    }

    @Id
    @Column(name = "openid_url", length = 100)
    public String getOpenIdUrl() {
        return openIdUrl;
    }

    public void setOpenIdUrl(String openIdUrl) {
        this.openIdUrl = openIdUrl;
    }


    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
