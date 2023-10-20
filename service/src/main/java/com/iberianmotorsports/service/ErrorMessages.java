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
    AUTH_TOKEN_UNABLE_TO_REFRESH(18, "unable to refresh auth token"),
    CAR_ID_NOT_FOUND(19, "car not found by Id"),
    CAR_MODEL_NOT_FOUND(20, "car not found by model"),
    GRID_ID_NOT_FOUND(21, "grid not found by Id"),
    GRID_DRIVER_NOT_ALLOWED(22, "only grid manager and the driver can perform this action"),
    GRID_DRIVER_HAS_GRID_ALREADY(23, "driver already has a grid entry for this championship"),
    GRID_CAR_NUMBER_IS_ALREADY_ON_USE(24, "car number is already on use"),
    GRID_CHAMPIONSHIP_IS_FULL(25, "championship grid is already full"),
    GRID_CAR_IS_NOT_ALLOWED_FOR_THIS_CHAMPIONSHIP(26, "car is not allowed for this championship"),
    GRID_USER_NOT_FOUND(27, "grid user not found"),
    USER_PROFILE_IS_NOT_COMPLETED(28, "user profile must be completed"),
    RACE_SESSION_TYPE_MISSING(29, "Each race must have all session type defined (P, Q, R)"),
    SANCTION_NOT_FOUND(30, "Sanction with id not found"),
    GRID_RACE_NOT_FOUND(31, "Grid for Race not found"),
    CHAMPIONSHIP_CATEGORY_MAX_CAR_SLOTS(32, "Max car slots for championship must match with Categories max slots"),
    CATEGORY_NOT_FOUND(33, "Category not found"),
    USER_IS_NOT_LOGGED(34, "User is not logged"),
    USER_GRID_REQUIRED_PERMISSION(35, "User is not Admin and doesn't belong to this grid"),
    GRID_DRIVERS_CAN_NOT_BE_EMPTY(36, "Grid Drivers can not be empty"),
    GRID_PASSWORD_INCORRECT(37, "Grid password is incorrect"),
    BOP_NOT_FOUND(38, "Bop not found"),
    DEFAULT(999, "");

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
