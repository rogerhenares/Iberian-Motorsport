package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.BopDTO;
import com.iberianmotorsports.service.model.Bop;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static com.iberianmotorsports.service.utils.GetBop.getBop;

@Service
@AllArgsConstructor
public class BopMapper implements Function<BopDTO, Bop> {

    @Override
    public Bop apply(BopDTO bopDTO) {
        Bop bop = new Bop();
        bop.setBallastKg(bopDTO.ballastKg());
        bop.setRestrictor(bopDTO.restrictor());
        bop.setBopPrimaryKey(getBop(bopDTO.raceId(), bopDTO.car().id()).getBopPrimaryKey());
        return bop;
    }

}
