package com.iberianmotorsports.service.model.composeKey;

import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
public class GridUserPrimaryKey implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "grid_id", referencedColumnName = "id")
    private Grid grid;
}
