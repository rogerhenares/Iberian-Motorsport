package com.iberianmotorsports.service.model.parsing.export;

import lombok.Data;

@Data
public class BopEntry {

    private String track;
    private Integer carModel;
    private Integer ballastKg;
    private Integer restrictor;

}
