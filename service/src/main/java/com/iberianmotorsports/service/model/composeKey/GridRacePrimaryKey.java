package com.iberianmotorsports.service.model.composeKey;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class GridRacePrimaryKey implements Serializable {

    private Long gridId;

    private Long raceId;
}
