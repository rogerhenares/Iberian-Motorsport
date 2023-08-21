package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.Car;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarService {

    List<Car> getCarsByCategories (List<String> category);

    Car getCarByModel(String model);

    Car getCarById(Long carId);

    Boolean isCarCategory(Long carId, String category);

    Boolean validateCategory(String category);
}
