package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.controller.DTO.ChampionshipDTO;
import com.iberianmotorsports.service.controller.DTO.Mappers.ChampionshipMapper;
import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.repository.ChampionshipRepository;
import com.iberianmotorsports.service.service.ChampionshipService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service("ChampionshipService")
public class ChampionshipServiceImpl implements ChampionshipService {

    @Autowired
    private ChampionshipRepository championshipRepository;

    private ChampionshipMapper championshipMapper;


    @Override
    public Championship saveChampionship(ChampionshipDTO championshipDTO) {
        Championship championship = championshipMapper.apply(championshipDTO);
        if (isInDatabase(championship.getName()))
            throw new ServiceException(ErrorMessages.DUPLICATED_CHAMPIONSHIP.getDescription());
        return championshipRepository.save(championship);
    }

    @Override
    public Championship findChampionshipById(Long id) {
        Optional<Championship> championshipOptional = championshipRepository.findById(id);
        if (championshipOptional.isEmpty())
            throw new ServiceException(ErrorMessages.CHAMPIONSHIP_NOT_IN_DB.getDescription());
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
    public Page<Championship> findAllChampionships(Pageable pageRequest) {
        return championshipRepository.findAll(pageRequest);
    }

    @Override
    public Championship updateChampionship(Championship championship) {
        return championshipRepository.save(championship);
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

}
