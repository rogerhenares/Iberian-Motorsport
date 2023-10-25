package com.iberianmotorsports;

import com.iberianmotorsports.service.model.Bop;
import com.iberianmotorsports.service.model.Car;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.composeKey.BopPrimaryKey;

import java.util.List;

public class BopFactory {

    private static final Race race = RaceFactory.race();
    private static final Car car = CarFactory.car();
    private static final Integer ballastKg = 10;
    private static final Integer restrictor = 0;

    public static Bop bop() {
        Bop bop = new Bop();
        bop.setBopPrimaryKey(new BopPrimaryKey(race, car));
        bop.setBallastKg(ballastKg);
        bop.setRestrictor(restrictor);
        return bop;
    }

    public static List<Bop> bopList() {
        return List.of(bop());
    }
}
