package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.Championship;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ChampionshipService {

    Championship saveChampionship(Championship championship);

    Championship findChampionshipById(Long id);

    Championship findChampionshipByName(String name);

    Page<Championship> findAllChampionships();

    Championship updateChampionship(Championship championship);

    void deleteChampionship(Long id);

    Boolean isAlreadyInDatabase(Long id);

    String exportChampionship(Championship championship) throws IOException;

}
