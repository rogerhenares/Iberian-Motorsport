package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.controller.DTO.GridDTO;
import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GridService {

    Grid getGridById(Long gridId);

    List<Grid> getGridForChampionship(Long championshipId);

    List<Grid> getGridForUser(User user);

    Grid findGridByPassword(String password);

    Grid createGridEntry(GridDTO gridDTO);

    Grid updateGrid(GridDTO gridDTO);

    Grid updateGridCar(Long gridId , Long carId);

    Grid updateGridCarNumber(Long gridId, Integer carNumber);

    void updateGridLicensePoints(Long gridId, Float licensePoints, Boolean isApply);

    void addDriver(Long gridId, Long steamId, String password);

    void removeDriver(Long gridId, Long steamId);

    void deleteGrid(Long gridId);

}
