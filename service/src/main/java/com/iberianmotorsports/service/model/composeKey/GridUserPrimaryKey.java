package com.iberianmotorsports.service.model.composeKey;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class GridUserPrimaryKey implements Serializable {

    private Long steamId;
    private Integer gridId;
}
