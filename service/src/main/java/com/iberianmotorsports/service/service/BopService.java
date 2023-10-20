package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.Bop;

import java.util.List;

public interface BopService {

    Bop getBop(Long carId, Long raceId);

    List<Bop> getBopForRace(Long raceId);

    Bop saveBop(Bop bop);

}
