package com.iberianmotorsports;

import com.iberianmotorsports.service.model.Car;

import java.util.ArrayList;
import java.util.List;

public class CarFactory {

    public static final Long id = 1L;
    public static final String manufacturer = "AMR";

    public static final String model = "V12 Vantage";
    public static final String category = "GT3";

    public static Car car() {
        Car car = new Car();
        car.setId(id);
        car.setManufacturer(manufacturer);
        car.setModel(model);
        car.setCategory(category);
        return car;
    }

    public static List<Car> carList(Integer numberOfCars) {

        List<Car> carList = new ArrayList<>();

        for (int i = 0; i < numberOfCars; i++) {
            Car car = CarFactory.car();
            carList.add(car);
        }
        return carList;
    }

    public static List<String> categoryList(Integer numberOfCars) {
        List<String> categoryList = new ArrayList<>();

        for (int i = 0; i < numberOfCars; i++) {
            Car car = CarFactory.car();
            categoryList.add(car.getCategory());
        }

        return categoryList;
    }

}
