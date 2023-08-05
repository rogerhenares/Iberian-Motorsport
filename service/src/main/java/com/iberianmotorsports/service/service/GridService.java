package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.controller.DTO.GridDTO;
import com.iberianmotorsports.service.model.Grid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GridService {

    Grid getGridById(Long gridId);

    List<Grid> getGridForChampionship(Long championshipId);

    Grid createGridEntry(GridDTO gridDTO);

    Grid updateGridCar(Long gridId , Long carId);

    Grid updateGridCarNumber(Long gridId, Integer carNumber);

    void addDriver(Long gridId, Long steamId);

    void removeDriver(Long gridId, Long steamId);

}
