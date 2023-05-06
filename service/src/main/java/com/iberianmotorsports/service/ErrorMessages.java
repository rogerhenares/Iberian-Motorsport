package com.iberianmotorsports.service;

public enum ErrorMessages {
    DATABASE(0, "A database error has occurred."),
    DUPLICATE_USER(1, "This user already exists."),
    USER_NOT_IN_DB(2, "This user does not exist."),
    STEAM_ID_UNDEFINED(3, "Steam Id is undefined."),
    FIRST_NAME(4, "First name cannot be empty."),
    CHAMPIONSHIP_NOT_IN_DB(5, "This Championship does not exist."),
    DUPLICATED_CHAMPIONSHIP(6, "This Championship already exists."),
    RACE_NOT_IN_DB(7, "This race does not exist."),
    DUPLICATED_RACE(8, "This race already exists."),
    RACE_RULES_NOT_IN_DB(9, "This race rules configuration does not exist."),
    DUPLICATED_RACE_RULES(10, "This race rules configuration already exists."),
    SESSION_NOT_IN_DB(11, "This session does not exist."),
    DUPLICATED_SESSION(12, "This session already exists.");


    private final int code;
    private final String description;

    private ErrorMessages(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}