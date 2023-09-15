package com.iberianmotorsports.service.model.parsing;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Driver {

    private String firstName;

    private String lastName;

    private String shortName;

    private Float driverCategory;

    @JsonProperty("playerID")
    @JsonAlias("playerId")
    private String playerID;

}
