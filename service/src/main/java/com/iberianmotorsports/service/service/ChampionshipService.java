package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.controller.DTO.ChampionshipDTO;
import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.model.criteria.CriteriaChampionship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ChampionshipService {

    Championship saveChampionship(ChampionshipDTO championshipDTO);

    Championship findChampionshipById(Long id);

    Championship findChampionshipByName(String name);

    Page<Championship> findChampionshipByCriteria(CriteriaChampionship criteriaChampionship, Pageable pageable);

    Page<Championship> findChampionshipByLoggedUser(Pageable pageable);

    Page<Championship> findAllChampionships(Pageable pageable);

    Championship updateChampionship(Championship championship);

    void deleteChampionship(Long id);

    Boolean isAlreadyInDatabase(Long id);

    String exportChampionship(Championship championship) throws IOException;

    //TODO implements points/position/results calculation
}
