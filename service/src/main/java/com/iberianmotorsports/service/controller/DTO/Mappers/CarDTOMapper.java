package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.CarDTO;
import com.iberianmotorsports.service.model.Car;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class CarDTOMapper implements Function<Car, CarDTO> {

    @Override
    public CarDTO apply(Car car) {

        return new CarDTO(
                car.getId(),
                car.getManufacturer(),
                car.getModel(),
                car.getCategory()
        );
    }
}
