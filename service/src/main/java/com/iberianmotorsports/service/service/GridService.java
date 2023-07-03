package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.controller.DTO.GridDTO;
import com.iberianmotorsports.service.model.Grid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GridService {

    List<Grid> getGridForChampionship(Long championshipId);

    GridDTO createGridEntry(GridDTO gridDTO);

    Grid updateGridEntry(Grid gridUpdate);

    void addDriver(Long gridId, Long steamId);

    void removeDriver(Long gridId, Long steamId);

}
