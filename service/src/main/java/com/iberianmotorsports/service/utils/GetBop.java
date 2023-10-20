package com.iberianmotorsports.service.utils;

import com.iberianmotorsports.service.model.Bop;
import com.iberianmotorsports.service.model.Car;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.composeKey.BopPrimaryKey;

public class GetBop {

    private GetBop() {

    }

    public static Bop getBop(Long raceId, Long carId) {
        Race race = new Race();
        race.setId(raceId);
        Car car = new Car();
        car.setId(carId);
        BopPrimaryKey bopPrimaryKey = new BopPrimaryKey(race, car);
        Bop bop = new Bop();
        bop.setBopPrimaryKey(bopPrimaryKey);
        return bop;
    }

}
