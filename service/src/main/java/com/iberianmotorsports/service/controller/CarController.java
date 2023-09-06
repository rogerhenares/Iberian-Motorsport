package com.iberianmotorsports.service.controller;

import com.google.protobuf.ServiceException;
import com.iberianmotorsports.service.controller.DTO.CarDTO;
import com.iberianmotorsports.service.controller.DTO.Mappers.CarDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.CarMapper;
import com.iberianmotorsports.service.model.Car;
import com.iberianmotorsports.service.model.parsing.Entry;
import com.iberianmotorsports.service.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/car")
@RequiredArgsConstructor
@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarDTOMapper carDTOMapper;

    @GetMapping(value = "category/{category}")
    public ResponseEntity<?> getCarByCategory(@PathVariable("category") List<String> categories) throws ServiceException {
        List<Car> carList = carService.getCarsByCategories(categories);
        List<CarDTO> carDTOList = carList.stream().map(carDTOMapper).toList();
        return new ResponseEntity<Object>(carDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "model/{model}")
    public ResponseEntity<?> getCarByModel(@PathVariable("model") String model) throws ServiceException {
        Car car = carService.getCarByModel(model);
        CarDTO carDTO = carDTOMapper.apply(car);
        return new ResponseEntity<Object>(carDTO, HttpStatus.OK);
    }

    @GetMapping(value = "id/{id}")
    public ResponseEntity<?> getCarById(@PathVariable("id") Long carId) throws ServiceException {
        Car car = carService.getCarById(carId);
        CarDTO carDTO = carDTOMapper.apply(car);
        return new ResponseEntity<Object>(carDTO, HttpStatus.OK);
    }

}
