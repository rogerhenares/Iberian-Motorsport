package com.iberianmotorsports.service.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum CarCategory {
    FREE("GT3;GT4;CUP;TCX;CHL;ST"),
    GT3("GT3"),
    GT4("GT4"),
    CUP("CUP"),
    TCX("TCX"),
    CHL("CHL"),
    ST("ST");

    private final String value;

    public List<String> getValue() {
        return List.of(this.value.split(";"));
    }
}
