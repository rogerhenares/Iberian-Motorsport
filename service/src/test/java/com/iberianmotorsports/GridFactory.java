package com.iberianmotorsports;

import com.iberianmotorsports.service.model.Car;
import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.model.User;

import java.util.ArrayList;
import java.util.List;

public class GridFactory {

    public static final Long id = 1L;
    public static final Integer carNumber = 1;
    public static final String carLicense = "testCarLicense";
    public static final Car car = CarFactory.car();

    public static final Championship championship = ChampionshipFactory.championship();
    public static final List<User> drivers = UserFactory.userList(5);

    public static Grid grid() {
        Grid grid = new Grid();
        grid.setId(id);
        grid.setCarNumber(carNumber);
        grid.setCarLicense(carLicense);
        grid.setCar(car);
        grid.setChampionship(championship);
        grid.setDrivers(drivers);
        return grid;
    }

    public static List<Grid> gridList(Integer numberOfGrids) {
        List<Grid> gridList = new ArrayList<>();
        for(int i = 0; i < numberOfGrids; i++) {
            Grid grid = GridFactory.grid();
            gridList.add(grid);
        }
        return gridList;
    }

}
