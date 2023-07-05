package com.iberianmotorsports.service.service.implementation;

import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.model.Car;
import com.iberianmotorsports.service.repository.CarRepository;
import com.iberianmotorsports.service.service.CarService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<Car> getCarsByCategories(List<String> categories) {
        return carRepository.findCarsByCategoryIn(categories);
    }

    @Override
    public Car getCarByModel(String model) {
        return carRepository.findCarByModel(model).orElseThrow(() ->
                new ServiceException(ErrorMessages.CAR_MODEL_NOT_FOUND.getDescription()));
    }

    @Override
    public Car getCarById(Long carId) {
        return carRepository.findById(carId).orElseThrow(() ->
                new ServiceException(ErrorMessages.CAR_ID_NOT_FOUND.getDescription()));
    }

    //TODO fix this to also consider when category has not restriction like mixed category
    @Override
    public Boolean isCarCategory(Long carId, String category) {
        Car car = getCarById(carId);
        return car.getCategory().equals(category);
    }
}
