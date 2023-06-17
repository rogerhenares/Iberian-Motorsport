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
    DUPLICATED_SESSION(12, "This session already exists."),
    STEAM_DATA(13, "Could not retrieve Steam data. Please try again."),
    LAST_NAME(14, "Last name cannot be empty."),
    SHORT_NAME(15, "Short name cannot be empty."),
    NATIONALITY(16, "Nationality cannot be empty."),
    AUTH_TOKEN_NOT_FOUND(17, "unable to found auth token"),
    AUTH_TOKEN_UNABLE_TO_REFRESH(18, "unable to refresh auth token");

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
