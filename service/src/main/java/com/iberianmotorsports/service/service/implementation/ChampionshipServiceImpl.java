package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.controller.DTO.ChampionshipDTO;
import com.iberianmotorsports.service.controller.DTO.Mappers.ChampionshipMapper;
import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.ChampionshipCategory;
import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.model.criteria.CriteriaChampionship;
import com.iberianmotorsports.service.repository.ChampionshipCategoryRepository;
import com.iberianmotorsports.service.repository.ChampionshipRepository;
import com.iberianmotorsports.service.service.CarService;
import com.iberianmotorsports.service.service.ChampionshipService;
import com.iberianmotorsports.service.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service("ChampionshipService")
public class ChampionshipServiceImpl implements ChampionshipService {

    private ChampionshipRepository championshipRepository;
    private ChampionshipCategoryRepository championshipCategoryRepository;
    private UserService userService;
    private CarService carService;
    private ChampionshipMapper championshipMapper;

    @Override
    public Championship saveChampionship(ChampionshipDTO championshipDTO) {
        Championship championship = championshipMapper.apply(championshipDTO);
//        if (isInDatabase(championship.getName()))
//            throw new ServiceException(ErrorMessages.DUPLICATED_CHAMPIONSHIP.getDescription());
        championship.setFinished(false);
        championship.setStarted(false);
        Championship createdChampionship = championshipRepository.save(championship);
        saveChampionshipCategoryForChamp(createdChampionship);
        return createdChampionship;
    }

    @Override
    public Championship findChampionshipById(Long id) {
        Optional<Championship> championshipOptional = championshipRepository.findById(id);
        if (championshipOptional.isEmpty())
            throw new ServiceException(ErrorMessages.CHAMPIONSHIP_NOT_IN_DB.getDescription());
        championshipOptional.get().setCarListForChampionship(
                carService.getCarsByCategories(
                        getCategoriesForChampionship(championshipOptional.get()))
        );
        return championshipOptional.orElse(null);
    }

    @Override
    public Championship findChampionshipByName(String name) {
        Optional<Championship> championshipOptional = championshipRepository.findByName(name);
        if (championshipOptional.isEmpty())
            throw new ServiceException(ErrorMessages.CHAMPIONSHIP_NOT_IN_DB.getDescription());
        return championshipOptional.orElse(null);
    }

    @Override
    public Page<Championship> findChampionshipByCriteria(CriteriaChampionship criteriaChampionship, Pageable pageable) {
        if(criteriaChampionship.getLogged()) {
            Long steamId;
            try{
                steamId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            } catch (Exception e) {
                throw new AuthenticationException(ErrorMessages.USER_IS_NOT_LOGGED.getDescription()) {};
            }
            User loggedUser = userService.findUserBySteamId(steamId);
            return championshipRepository.findByLoggedUser(loggedUser, pageable);
        }
        return championshipRepository.findByDisabledAndStartedAndFinished(
                criteriaChampionship.getDisabled(),
                criteriaChampionship.getStarted(),
                criteriaChampionship.getFinished(),
                LocalDateTime.now(),
                pageable);
    }

    @Override
    public Page<Championship> findChampionshipByLoggedUser(Pageable pageable) {
        Long steamId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = userService.findUserBySteamId(steamId);

        Page<Championship> championshipForLoggedUser =
                championshipRepository.findByLoggedUser(loggedUser, pageable);
        return championshipForLoggedUser;
    }

    @Override
    public Page<Championship> findAllChampionships(Pageable pageRequest) {
        return championshipRepository.findAll(pageRequest);
    }

    @Override
    public Championship updateChampionship(Championship championship) {
        championship.setRaceList(championshipRepository.getById(championship.getId()).getRaceList());
        Championship updatedChampionship = championshipRepository.save(championship);
        updatedChampionship.getRaceList().size();
        return updatedChampionship;
    }

    @Override
    public void deleteChampionship(Long id) {
        championshipRepository.deleteById(id);
    }

    @Override
    public Boolean isAlreadyInDatabase(Long id) {
        return null;
    }

    public Boolean isInDatabase(String name) {
        Optional<Championship> championshipOptional = championshipRepository.findByName(name);
        return championshipOptional.isPresent();
    }

    @Override
    public String exportChampionship(Championship championship) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(championship);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath() + "/settings.json";
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(json);
            fileWriter.close();
            return "Championship saved to " + filePath;
        } else {
            return "No directory selected";
        }
    }

    private List<String> getCategoriesForChampionship(Championship championship) {
        return championship.getCategoryList().stream()
                .map(ChampionshipCategory::getCategory)
                .toList();
    }

    private void saveChampionshipCategoryForChamp(Championship championship) {
        Integer totalCarsFromCategories = championship.getCategoryList()
                .stream()
                .mapToInt(ChampionshipCategory::getMax)
                .sum();
        if(championship.getMaxCarSlots().equals(totalCarsFromCategories)) {
            throw new ServiceException(ErrorMessages.GRID_CHAMPIONSHIP_IS_FULL.getDescription());
        }
        List<ChampionshipCategory> championshipCategoryList = championship.getCategoryList().stream()
                .map(championshipCategory -> {
                    if(carService.validateCategory(championshipCategory.getCategory())){
                        throw new ServiceException(ErrorMessages.CATEGORY_NOT_FOUND.getDescription());
                    }
                    championshipCategory.setChampionship(championship);
                    return championshipCategory;
                })
                .toList();
        this.championshipCategoryRepository.saveAll(championshipCategoryList);
    }
}
