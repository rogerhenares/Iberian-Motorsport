package com.iberianmotorsports.service.features.car;

import com.iberianmotorsports.CarFactory;
import com.iberianmotorsports.service.model.Car;
import com.iberianmotorsports.service.repository.CarRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:it.properties")
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    public void setupDataBase() {
        Car car = CarFactory.car();
    }

    @Test
    void save() {
        Car car = CarFactory.car();
        car.setId(null);
        Car savedCar = carRepository.save(car);
        assertEquals(savedCar, car);
    }

    @Test
    void getCarById() {
        Car car = CarFactory.car();
        Long id = car.getId();
        Optional<Car> foundCar = carRepository.findById(id);
        assertTrue(foundCar.isPresent());
        Assertions.assertThat(foundCar.get()).isEqualTo(car);
    }

    @Test
    void findCarsByCategoryIn() {
        Car car = CarFactory.car();
        List<String> categories = new ArrayList<>();
        categories.add(car.getCategory());
        List<Car> foundCars = carRepository.findCarsByCategoryIn(categories);
        assertFalse(foundCars.isEmpty());
        Assertions.assertThat(foundCars.get(0)).isEqualTo(car);
    }

    @Test
    void findCarByModel() {
        Car car = CarFactory.car();
        String model = car.getModel();
        Optional<Car> foundCar = carRepository.findCarByModel(model);
        assertTrue(foundCar.isPresent());
        Assertions.assertThat(foundCar.get()).isEqualTo(car);
    }

    @Test
    void deleteCar() {
        Car car = CarFactory.car();
        assertDoesNotThrow(()-> carRepository.delete(car));
    }

}
