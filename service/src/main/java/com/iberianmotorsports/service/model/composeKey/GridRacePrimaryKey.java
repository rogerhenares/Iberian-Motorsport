package com.iberianmotorsports.service.model.composeKey;


import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.model.Race;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Data
public class GridRacePrimaryKey implements Serializable {

    @ManyToOne
    @JoinColumn(name = "grid_id", referencedColumnName = "id")
    private Grid grid;

    @ManyToOne
    @JoinColumn(name = "race_id", referencedColumnName = "id")
    private Race race;
}
