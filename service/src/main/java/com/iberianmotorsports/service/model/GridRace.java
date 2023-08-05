package com.iberianmotorsports.service.model;

import com.iberianmotorsports.service.model.composeKey.GridRacePrimaryKey;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "GRID_RACE")
public class GridRace implements Serializable {

    @EmbeddedId
    private GridRacePrimaryKey gridRacePrimaryKey;

    @Column(name = "points", nullable = false)
    private Long points;

    @Column(name = "first_sector", nullable = false)
    private Long firstSector;

    @Column(name = "second_sector", nullable = false)
    private Long secondSector;

    @Column(name = "third_sector", nullable = false)
    private Long thirdSector;

    @Column(name = "final_time", nullable = false)
    //TODO finalTime in DTO should include sanctions (+30)
    // Final Time: 30:01:432 (+12)
    private Long finalTime;

    @Column(name = "total_laps", nullable = false)
    private Integer totalLaps;

    @OneToMany(mappedBy = "gridRace", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    private List<Sanction> sanctionList;

    @ManyToOne
    @JoinColumn(name = "race_id", referencedColumnName = "id")
    private Race race;

    @ManyToOne
    @JoinColumn(name = "grid_id", referencedColumnName = "id")
    private Grid grid;
}
