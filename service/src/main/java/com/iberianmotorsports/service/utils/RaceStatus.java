package com.iberianmotorsports.service.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RaceStatus {
    PENDING, //TO BE CREATED
    EXPORTING, //PREPARING EXPORT FILES
    EXPORT_FAILED, //FAIL TO EXPORT FILES
    LAUNCHED, //LAUNCHED SERVER .exe
    STOP, //STOP SERVER .exe
    STOP_FAILED, //STOP FAILED SERVER .exe
    IMPORTING, //IMPORTING RESULTS DATA
    IMPORT_FAILED, //FAIL TO IMPORT RESULTS
    COMPLETED; //COMPLETED ALL RACE STEPS
}
