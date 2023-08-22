package com.iberianmotorsports.service.model;

import com.iberianmotorsports.service.model.composeKey.GridUserPrimaryKey;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "GRID_USER")
public class GridUser implements Serializable {

    @EmbeddedId
    private GridUserPrimaryKey primaryKey;

    @Column(name = "grid_manager", nullable = false)
    private boolean gridManager;
}
