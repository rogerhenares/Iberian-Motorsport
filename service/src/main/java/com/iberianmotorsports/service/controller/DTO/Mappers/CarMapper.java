package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.CarDTO;
import com.iberianmotorsports.service.model.Car;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class CarMapper implements Function<CarDTO, Car> {

    @Override
    public Car apply(CarDTO carDTO){
        Car car = new Car();
        car.setId(carDTO.id());
        car.setManufacturer(carDTO.manufacturer());
        car.setModel(carDTO.model());
        car.setCategory(carDTO.category());
        return car;
    }

}
