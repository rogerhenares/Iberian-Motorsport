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

    @Column(name = "qualy_position")
    private Integer qualyPosition;

    @Column(name = "qualy_time")
    private Long qualyTime;

    @Column(name = "qualy_first_sector")
    private Long qualyFirstSector;

    @Column(name = "qualy_second_sector")
    private Long qualySecondSector;

    @Column(name = "qualy_third_sector")
    private Long qualyThirdSector;

    @Column(name = "sanction_time")
    private Integer sanctionTime = 0;

    @Column(name = "drop_round")
    private Boolean dropRound = false;

    @Column(name = "fast_lap")
    private Boolean fastLap = false;

    @OneToMany(mappedBy = "gridRace", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Sanction> sanctionList;

    @Transient Long timeWithPenalties;
}
