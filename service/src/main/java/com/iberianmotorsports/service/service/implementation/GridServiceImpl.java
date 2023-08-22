package com.iberianmotorsports.service.service.implementation;

import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.controller.DTO.GridDTO;
import com.iberianmotorsports.service.controller.DTO.Mappers.GridDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.GridMapper;
import com.iberianmotorsports.service.model.*;
import com.iberianmotorsports.service.model.composeKey.GridUserPrimaryKey;
import com.iberianmotorsports.service.repository.GridRepository;
import com.iberianmotorsports.service.repository.GridUserRepository;
import com.iberianmotorsports.service.repository.SanctionRepository;
import com.iberianmotorsports.service.service.CarService;
import com.iberianmotorsports.service.service.ChampionshipService;
import com.iberianmotorsports.service.service.GridService;
import com.iberianmotorsports.service.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.iberianmotorsports.service.ErrorMessages.GRID_DRIVER_NOT_ALLOWED;

@AllArgsConstructor
@Transactional
@Service
public class GridServiceImpl  implements GridService {

    ChampionshipService championshipService;
    UserService userService;
    CarService carService;
    GridRepository gridRepository;
    GridUserRepository gridUserRepository;
    GridMapper gridMapper;
    GridDTOMapper gridDTOMapper;
    SanctionRepository sanctionRepository;

    @Override
    public Grid getGridById(Long gridId) {
        return gridRepository.findById(gridId)
                .orElseThrow(() -> new ServiceException(ErrorMessages.GRID_ID_NOT_FOUND.getDescription()));
    }

    @Override
    public List<Grid> getGridForChampionship(Long championshipId) {
        List<Grid> grids = gridRepository.findGridsByChampionshipId(championshipId);
        grids = grids.stream()
                .map(grid -> {
                    grid.setPoints(grid.getGridRaceList().stream().mapToDouble(GridRace::getPoints).sum());
                    return grid;
                }).toList();
        return grids;
    }

    @Override
    public Grid createGridEntry(@Valid GridDTO gridDTO) {
        Grid grid = gridMapper.apply(gridDTO);
        validateGridForChampionship(grid);
        loadDriversForGrid(grid);
        grid.setChampionship(championshipService.findChampionshipById(grid.getChampionship().getId()));
        grid.setCar(carService.getCarById(grid.getCar().getId()));
        grid.setCarLicense("PRO");
        grid.setDisabled(Boolean.FALSE);
        Grid createdGrid = gridRepository.saveAndFlush(grid);
        setGridManager(grid.getDrivers().stream().findFirst().get(), grid);
        return grid;
    }

    @Override
    public void addDriver(Long gridId, Long steamId) {
        Grid grid = getGrid(gridId);
        driverValidForChampionship(steamId, grid.getChampionship().getId());
        isDriverOrGridManager(steamId, grid);
        User driverToAdd = userService.findUserBySteamId(steamId);
        grid.getDrivers().add(driverToAdd);
        gridRepository.save(grid);
    }

    @Override
    public void removeDriver(Long gridId, Long steamId) {
        Grid grid = getGrid(gridId);
        isDriverOrGridManager(steamId, grid);
        User driverToRemove = userService.findUserBySteamId(steamId);
        grid.getDrivers().remove(driverToRemove);
        gridRepository.save(grid);
    }

    @Override
    public Grid updateGridCar(Long gridId, Long carId) {
        Grid grid = getGrid(gridId);
        Car carToUpdate = carService.getCarById(carId);
        validateCarForGrid(grid);
        grid.setCar(carToUpdate);
        gridRepository.save(grid);
        return grid;
    }

    @Override
    public Grid updateGridCarNumber(Long gridId, Integer carNumber) {
        Grid grid = getGrid(gridId);
        validateCarNumberForChampionship(grid.getChampionship().getId(), carNumber);
        grid.setCarNumber(carNumber);
        return grid;
    }

    @Override
    public void deleteGrid(Long gridId) {
        Grid grid = getGrid(gridId);
        if(grid.getGridRaceList().isEmpty()) {
            gridRepository.delete(grid);
        } else {
            grid.setDisabled(Boolean.TRUE);
            gridRepository.save(grid);
        }
    }

    private void validateCarNumberForChampionship(Long championshipId, Integer carNumber){
        Optional<Grid> optionalGrid = gridRepository.findGridByChampionshipIdAndCarNumber(championshipId, carNumber);
        if(optionalGrid.isPresent()) {
            throw new ServiceException(ErrorMessages.GRID_CAR_NUMBER_IS_ALREADY_ON_USE.getDescription());
        }
    }

    private Grid getGrid(Long gridId) {
        return gridRepository.findById(gridId).orElseThrow(() ->
                new ServiceException(ErrorMessages.GRID_ID_NOT_FOUND.getDescription()));
    }

    private void isDriverOrGridManager(Long driverSteamId, Grid grid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long loggedSteamId = Long.valueOf((String) authentication.getPrincipal());

        if(!driverSteamId.equals(loggedSteamId) &&
            !getGridManager(grid).getSteamId().equals(loggedSteamId)){
            throw new ServiceException(GRID_DRIVER_NOT_ALLOWED.getDescription());
        }

    }

    private User getGridManager(Grid grid) {
        GridUser gridManager = gridUserRepository.findGridUserByPrimaryKeyGridIdAndGridManagerTrue(grid.getId().intValue());
        return gridManager.getPrimaryKey().getUser();
    }

    private Boolean isDriverOnChampionship(Long steamId, Long championshipId){
        User driver = userService.findUserBySteamId(steamId);
        Optional<Grid> optionalGridDriver = gridRepository.findGridByChampionshipIdAndDriversContains(championshipId, driver);
        return optionalGridDriver.isPresent();
    }

    //TODO include here all others restrictions like license, safety rating, and category.
    private void driverValidForChampionship(Long steamId, Long championshipId) {
        if(isDriverOnChampionship(steamId, championshipId)){
            throw new ServiceException(ErrorMessages.GRID_DRIVER_HAS_GRID_ALREADY.getDescription());
        }
        if(!userService.isProfileCompleted(steamId)){
            throw new ServiceException(ErrorMessages.USER_PROFILE_IS_NOT_COMPLETED.getDescription());
        }
    }

    private Boolean isChampionshipGridFull(Long championshipId){
        List<Grid> championshipGrid = getGridForChampionship(championshipId);
        if(!championshipGrid.isEmpty()){
            if(championshipGrid.get(0).getChampionship().getMaxCarSlots() >= championshipGrid.size()){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public List<Grid> getGridForUser(User user) {
        return gridRepository.findGridsByDriversContainsAndDisabledIsFalse(user);
    }

    private void setGridManager (User driver, Grid grid) {
        GridUserPrimaryKey gridUserPrimaryKey = new GridUserPrimaryKey(driver, grid);
        GridUser gridManager = gridUserRepository.findById(gridUserPrimaryKey).orElseThrow(() ->
                new ServiceException(ErrorMessages.GRID_USER_NOT_FOUND.getDescription()));
        gridManager.setGridManager(Boolean.TRUE);
        gridUserRepository.save(gridManager);
    }

    private void validateGridForChampionship(Grid grid) {
        for (User driver: grid.getDrivers()) {
            driverValidForChampionship(driver.getSteamId(), grid.getChampionship().getId());
        }
        validateCarNumberForChampionship(grid.getChampionship().getId(), grid.getCarNumber());
        validateCarForGrid(grid);
        if(isChampionshipGridFull(grid.getChampionship().getId())){
            throw new ServiceException(ErrorMessages.GRID_CHAMPIONSHIP_IS_FULL.getDescription());
        }
    }

    private void validateCarForGrid(Grid grid){
        if(carService.isCarCategory(grid.getCar().getId(), grid.getChampionship().getCarGroup())){
            throw new ServiceException(ErrorMessages.GRID_CAR_IS_NOT_ALLOWED_FOR_THIS_CHAMPIONSHIP.getDescription());
        }
        //TODO validate if car is available for charity
    }

    private void loadDriversForGrid(Grid grid) {
        grid.setDrivers(grid.getDrivers().stream()
                .map(driver -> userService.findUserBySteamId(driver.getSteamId()))
                .toList());
    }
}
