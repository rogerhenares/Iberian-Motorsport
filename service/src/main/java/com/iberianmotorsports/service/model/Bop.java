package com.iberianmotorsports.service.model;

import com.iberianmotorsports.service.model.composeKey.BopPrimaryKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "BOP")
public class Bop implements Serializable {

    @EmbeddedId
    private BopPrimaryKey bopPrimaryKey;

    @Column(name = "ballastKg", columnDefinition = "integer default 0")
    private Integer ballastKg;

    @Column(name = "restrictor", columnDefinition = "integer default 0")
    private Integer restrictor;

}
