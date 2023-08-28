package com.iberianmotorsports.service.utils;

import com.iberianmotorsports.service.model.User;

public enum RoleType {
    BASIC_USER("BASIC_USER"),
    ADMIN("ADMIN"),
    STEWARD("STEWARD");

    private final String value;

    RoleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Boolean isAdmin(User user) {
        return user.getRoles().stream().anyMatch(role -> role.getRole().equals(ADMIN.getValue()));
    }

    public Boolean isSteward(User user) {
        return user.getRoles().stream().anyMatch(role -> role.getRole().equals(STEWARD.getValue()));
    }
}
