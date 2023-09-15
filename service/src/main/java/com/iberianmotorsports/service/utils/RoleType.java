package com.iberianmotorsports.service.utils;

import com.iberianmotorsports.service.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

    public static Boolean isAdmin(User user) {
        return user.getRoles().stream().anyMatch(role -> role.getRole().equals(ADMIN.getValue()));
    }

    public static Boolean isSteward(User user) {
        return user.getRoles().stream().anyMatch(role -> role.getRole().equals(STEWARD.getValue()));
    }

    public static Boolean isAdminFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().anyMatch(role -> role.toString().equals(ADMIN.getValue()));
    }

    public static Boolean isStewardFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().anyMatch(role -> role.toString().equals(STEWARD.getValue()));
    }

}
