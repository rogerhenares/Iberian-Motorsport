package com.iberianmotorsports.service.service.implementation;

import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.model.Bop;
import com.iberianmotorsports.service.model.Car;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.composeKey.BopPrimaryKey;
import com.iberianmotorsports.service.repository.BopRepository;
import com.iberianmotorsports.service.service.BopService;
import com.iberianmotorsports.service.service.CarService;
import com.iberianmotorsports.service.service.RaceService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class BopServiceImpl implements BopService {

    BopRepository bopRepository;

    CarService carService;
    RaceService raceService;


    @Override
    public Bop getBop(Long carId, Long raceId) {
        Race race = raceService.findRaceById(raceId);
        Car car = carService.getCarById(carId);
        BopPrimaryKey id = new BopPrimaryKey(race, car);
        return bopRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorMessages.BOP_NOT_FOUND.getDescription()));
    }

    @Override
    public List<Bop> getBopForRace(Long raceId) {
        Race race = raceService.findRaceById(raceId);
        return bopRepository.findBopByBopPrimaryKey_Race(race);
    }

    @Override
    public Bop saveBop(Bop bop) {
        return bopRepository.save(bop);
    }

}
