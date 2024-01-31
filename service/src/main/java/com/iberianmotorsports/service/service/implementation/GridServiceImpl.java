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
import com.iberianmotorsports.service.utils.ChampionshipStyleType;
import com.iberianmotorsports.service.utils.RoleType;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.iberianmotorsports.service.ErrorMessages.GRID_DRIVER_NOT_ALLOWED;
import static com.iberianmotorsports.service.ErrorMessages.GRID_PASSWORD_INCORRECT;

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
        Grid gridToReturn = gridRepository.findById(gridId)
                .orElseThrow(() -> new ServiceException(ErrorMessages.GRID_ID_NOT_FOUND.getDescription()));
        gridToReturn.setManagerId(getGridManager(gridToReturn).getUserId());
        return gridToReturn;
    }

    @Override
    public List<Grid> getGridForChampionship(Long championshipId) {
        List<Grid> grids = gridRepository.findGridsByChampionshipId(championshipId);
        grids = grids.stream()
                .map(grid -> {
                    grid.setPoints(grid.getGridRaceList().stream()
                            .filter(gridRace -> !gridRace.getDropRound())
                            .mapToDouble(GridRace::getPoints)
                            .sum());
                    grid.setPointsDrop(grid.getGridRaceList().stream()
                            .mapToDouble(GridRace::getPoints)
                            .sum());
                    grid.setManagerId(getGridManager(grid).getUserId());
                    return grid;
                }).toList();
        return grids;
    }


    @Override
    public Grid findGridByPassword(String password) {
        return gridRepository.findGridByPassword(password);
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

        if (!ChampionshipStyleType.SOLO.getValue().equals(grid.getChampionship().getStyle())) {
            String generatedString = RandomStringUtils.random(5, true, true);
            while (findGridByPassword(generatedString) != null) {
                generatedString = RandomStringUtils.random(5, true, true);
            }
            grid.setPassword(generatedString);
        }

        Grid createdGrid = gridRepository.saveAndFlush(grid);
        setGridManager(createdGrid.getDrivers().stream().findFirst().get(), createdGrid);

        return createdGrid;
    }

    @Override
    public void addDriver(Long gridId, Long steamId, String password) {
        Grid grid = getGridById(gridId);
        driverValidForChampionship(steamId, grid.getChampionship().getId());
        if (isPasswordCorrect(password, grid)) {
            User driverToAdd = userService.findUserBySteamId(steamId);
            addGridUserToGrid(grid, driverToAdd);
        }
        else {
            throw new ServiceException(GRID_PASSWORD_INCORRECT.getDescription());
        }
    }

    @Override
    public void removeDriver(Long gridId, Long steamId) {
        Grid grid = getGridById(gridId);
        User manager = getGridManager(grid);
        validateLoggedUserFromGrid(grid);
        isDriverOrGridManager(steamId, grid);
        User driverToRemove = userService.findUserBySteamId(steamId);
        if(grid.getDrivers().size() == 1) {
            deleteGrid(gridId);
            return;
        }
        if(driverToRemove.equals(manager)){
            setGridManager(grid.getDrivers().stream().filter(driver -> !driver.equals(manager)).toList().get(0), grid);
        }
        gridUserRepository.deleteById(new GridUserPrimaryKey(driverToRemove, grid));
    }

    @Override
    public Grid updateGrid(GridDTO gridDTO) {
        Grid grid = gridMapper.apply(gridDTO);
        Grid gridToUpdate = getGridById(grid.getId());
        Car carToUpdate = carService.getCarById(grid.getCar().getId());
        List<Grid> gridTeamSolo = List.of();
        validateLoggedUserFromGrid(gridToUpdate);
        if (!Objects.equals(grid.getCarNumber(), gridToUpdate.getCarNumber())) {
            validateCarNumberForChampionship(gridToUpdate.getChampionship().getId(), grid.getCarNumber());
        }
        boolean isTeamSoloChampionship = ChampionshipStyleType.TEAM_SOLO.getValue().equals(gridToUpdate.getChampionship().getStyle());
        boolean isTeamChampionship = ChampionshipStyleType.TEAM.getValue().equals(gridToUpdate.getChampionship().getStyle());
        if (isTeamSoloChampionship) {
            gridTeamSolo = gridRepository.findGridsByChampionshipIdAndTeamName(
                    gridToUpdate.getChampionship().getId(),
                    gridToUpdate.getTeamName());
        }
        if (!gridToUpdate.getChampionship().getStarted() || RoleType.isAdminFromAuthentication()) {
            if(isTeamSoloChampionship) {
                gridTeamSolo = gridTeamSolo.stream().map(gridTeam -> {
                    gridTeam.setTeamName(grid.getTeamName());
                    gridTeam.setCar(carToUpdate);
                    return gridTeam;
                }).toList();
            }else {
                gridToUpdate.setTeamName(grid.getTeamName());
                gridToUpdate.setCar(grid.getCar());
            }
            gridToUpdate.setCarNumber(grid.getCarNumber());
        } else {
            if(isTeamSoloChampionship) {
                gridTeamSolo = gridTeamSolo.stream().map(gridTeam -> {
                    gridTeam.setCar(carToUpdate);
                    return gridTeam;
                }).toList();
            }else {
                gridToUpdate.setTeamName(grid.getTeamName());
            }
            gridToUpdate.setTeamName(grid.getTeamName());
        }
        if (RoleType.isAdminFromAuthentication()) {
            gridToUpdate.setCarLicense(grid.getCarLicense());
        }
        if (grid.getNewManagerId() != null){
            GridUser gridUser = gridUserRepository.findGridUserByPrimaryKeyGridIdAndGridManagerTrue(grid.getId().intValue());
            gridUser.setGridManager(Boolean.FALSE);
            gridUserRepository.save(gridUser);

            setGridManager(userService.findUserById(grid.getNewManagerId()), gridToUpdate);
        }
        if(isTeamSoloChampionship) {
            gridRepository.saveAll(gridTeamSolo);
        } else {
            gridRepository.save(gridToUpdate);
        }
        return gridToUpdate;
    }

    @Override
    public Grid updateGridCar(Long gridId, Long carId) {
        Grid grid = getGridById(gridId);
        validateLoggedUserFromGrid(grid);
        Car carToUpdate = carService.getCarById(carId);
        validateCarForGrid(grid);
        grid.setCar(carToUpdate);
        gridRepository.save(grid);
        return grid;
    }

    @Override
    public Grid updateGridCarNumber(Long gridId, Integer carNumber) {
        Grid grid = getGridById(gridId);
        validateLoggedUserFromGrid(grid);
        validateCarNumberForChampionship(grid.getChampionship().getId(), carNumber);
        grid.setCarNumber(carNumber);
        return grid;
    }

    @Override
    public void updateGridLicensePoints(Long gridId, Float licensePoints, Boolean isApply) {
        Grid grid = getGridById(gridId);
        grid.setLicensePoints(isApply ?
                grid.getLicensePoints() - licensePoints :
                grid.getLicensePoints() + licensePoints
        );
        gridRepository.save(grid);
    }

    @Override
    public void deleteGrid(Long gridId) {
        Grid grid = getGridById(gridId);
        validateLoggedUserFromGrid(grid);

        if(grid.getChampionship().getStarted()) {
            grid.setDisabled(Boolean.TRUE);
            gridRepository.save(grid);
        } else {
            gridRepository.delete(grid);
        }
    }

    private void validateLoggedUserFromGrid(Grid grid){
        Long loggedUserSteamId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = userService.findUserBySteamId(loggedUserSteamId);
        if(!grid.getDrivers().stream()
                .filter(user -> loggedUser.getSteamId().equals(user.getSteamId()))
                .toList().isEmpty()){
            return;
        }
        if(RoleType.isAdminFromAuthentication()){
            return;
        }
        throw new ServiceException(ErrorMessages.USER_GRID_REQUIRED_PERMISSION.getDescription());
    }

    private void validateCarNumberForChampionship(Long championshipId, Integer carNumber){
        Optional<Grid> optionalGrid = gridRepository.findGridByChampionshipIdAndCarNumber(championshipId, carNumber);
        if(optionalGrid.isPresent()) {
            throw new ServiceException(ErrorMessages.GRID_CAR_NUMBER_IS_ALREADY_ON_USE.getDescription());
        }
    }

    private void isDriverOrGridManager(Long driverSteamId, Grid grid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long loggedSteamId = Long.valueOf(authentication.getPrincipal().toString());

        if(!driverSteamId.equals(loggedSteamId) &&
            !getGridManager(grid).getSteamId().equals(loggedSteamId)){
            throw new ServiceException(GRID_DRIVER_NOT_ALLOWED.getDescription());
        }

    }

    private User getGridManager(Grid grid) {
        GridUser gridManager = gridUserRepository.findGridUserByPrimaryKeyGridIdAndGridManagerTrue(grid.getId().intValue());
        return gridManager.getPrimaryKey().getUser();
    }

    private void addGridUserToGrid(Grid grid, User user) {
        GridUserPrimaryKey pk = new GridUserPrimaryKey(user, grid);
        GridUser gridUser = new GridUser();
        gridUser.setPrimaryKey(pk);
        gridUser.setGridManager(Boolean.FALSE);
        gridUserRepository.save(gridUser);
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
            if(championshipGrid.get(0).getChampionship().getMaxCarSlots() <= championshipGrid.size()){
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

    private boolean isPasswordCorrect(String password, Grid grid){
        return password.equals(grid.getPassword());
    }

}
