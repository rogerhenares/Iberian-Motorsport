package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.BopDTO;
import com.iberianmotorsports.service.model.Bop;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class BopDTOMapper implements Function<Bop, BopDTO> {

    @Override
    public BopDTO apply(Bop bop){
        return new BopDTO(
                bop.getBopPrimaryKey().getRace().getId(),
                bop.getBopPrimaryKey().getCar(),
                bop.getBallastKg(),
                bop.getRestrictor()
        );
    }
}
