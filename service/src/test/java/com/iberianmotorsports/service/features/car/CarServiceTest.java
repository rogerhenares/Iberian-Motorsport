package com.iberianmotorsports.service.features.car;

import com.iberianmotorsports.CarFactory;
import com.iberianmotorsports.service.controller.DTO.Mappers.CarDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.CarMapper;
import com.iberianmotorsports.service.model.Car;
import com.iberianmotorsports.service.repository.CarRepository;
import com.iberianmotorsports.service.service.CarService;
import com.iberianmotorsports.service.service.implementation.CarServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    private CarService service;

    @Mock
    private CarRepository carRepository;

    private CarMapper carMapper;

    private CarDTOMapper carDTOMapper;

    @Captor
    ArgumentCaptor<Car> carCaptor;

    @BeforeEach
    public void init() {
        carMapper = new CarMapper();
        carDTOMapper = new CarDTOMapper();
        service = new CarServiceImpl(carRepository);
    }

    @Nested
    public class findCar {

        @Test
        public void getCarsByCategories() {
            when(carRepository.findCarsByCategoryIn(CarFactory.categoryList(5))).thenReturn(CarFactory.carList(5));

            carRepository.findCarsByCategoryIn(CarFactory.categoryList(5));

            verify(carRepository).findCarsByCategoryIn(CarFactory.categoryList(5));

        }

        @Test
        public void getCarByModel() {
            when(carRepository.findCarByModel(anyString())).thenReturn(Optional.of(CarFactory.car()));

            carRepository.findCarByModel(anyString());

            verify(carRepository).findCarByModel(anyString());
        }

        @Test
        public void getCarById() {
            when(carRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(CarFactory.car()));

            carRepository.findById(ArgumentMatchers.anyLong());

            verify(carRepository).findById(ArgumentMatchers.anyLong());
        }

    }

}
