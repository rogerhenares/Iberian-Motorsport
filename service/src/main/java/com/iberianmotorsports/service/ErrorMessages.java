package com.iberianmotorsports.service;

public enum ErrorMessages {
    DATABASE(0, "A database error has occurred."),
    DUPLICATE_USER(1, "This user already exists."),
    USER_NOT_IN_DB(2, "This user does not exist."),
    STEAM_ID_UNDEFINED(3, "Steam Id is undefined."),
    FIRST_NAME(4, "First name cannot be empty."),
    LAST_NAME(5, "Last name cannot be empty."),
    SHORT_NAME(6,"Short name cannot be empty."),
    NATIONALITY(7, "Nationality cannot be empty.");

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