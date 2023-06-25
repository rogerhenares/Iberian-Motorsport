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

    @ManyToOne
    @JoinColumn(name = "steam_id", referencedColumnName = "steam_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "grid_id", referencedColumnName = "id")
    private Role role;
}
