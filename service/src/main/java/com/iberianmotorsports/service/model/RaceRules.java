package com.iberianmotorsports.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Entity
@Data
public class RaceRules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "race_id")
    private Race race;

    @NotNull
    @Range(min=1, max=2)
    @Column(name = "qualify_standing_type")
    private Long qualifyStandingType;

    @NotNull
    @Range(min=-1)
    @Column(name = "pit_window_length_sec")
    private Integer pitWindowLengthSec;

    @NotNull
    @Range(min= -1)
    @Column(name = "driver_stint_time_sec")
    private Integer driverStintTimeSec;

    @NotNull
    @Range(min=0)
    @Column(name = "mandatory_pitstop_cout")
    private Integer mandatoryPitstopCount;

    @NotNull
    @Range(min=0)
    @Column(name = "max_total_driving_time")
    private Integer maxTotalDrivingTime;

    @NotNull
    @Range(min=1)
    @Column(name = "max_drivers_count")
    private Integer maxDriversCount;

    @NotNull
    @Range(min=0, max=1)
    @Column(name = "is_refuelling_allowed_in_race")
    private Integer isRefuellingAllowedInRace;

    @NotNull
    @Range(min=0, max=1)
    @Column(name = "is_refuelling_time_fixed")
    private Integer isRefuellingTimeFixed;

    @NotNull
    @Range(min=0, max=1)
    @Column(name = "is_mandatory_pitstop_refuelling_required")
    private Integer isMandatoryPitstopRefuellingRequired;

    @NotNull
    @Range(min=0, max=1)
    @Column(name = "is_mandatory_pitstop_tyre_change_required")
    private Integer isMandatoryPitstopTyreChangeRequired;

    @NotNull
    @Range(min=0, max=1)
    @Column(name = "is_mandatory_pitstop_swap_driver_required")
    private Integer isMandatoryPitstopSwapDriverRequired;

    @Column(name = "tyre_set_count")
    private Integer tyreSetCount;

}
