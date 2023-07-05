package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.controller.DTO.ChampionshipDTO;
import com.iberianmotorsports.service.controller.DTO.Mappers.ChampionshipDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.ChampionshipMapper;
import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.controller.DTO.MessageResponse;
import com.iberianmotorsports.service.service.ChampionshipService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/championship")
@RequiredArgsConstructor
@RestController
public class ChampionshipController {

    @Autowired
    private ChampionshipService championshipService;

    @Autowired
    private ChampionshipDTOMapper championshipDTOMapper;

    @Autowired
    private ChampionshipMapper championshipMapper;

    @PostMapping
    public ResponseEntity<?> createNewChampionship(@RequestBody ChampionshipDTO championshipDTO) {
        Championship createdChampionship = championshipService.saveChampionship(championshipDTO);
        ChampionshipDTO createdChampionshipDTO = championshipDTOMapper.apply(createdChampionship);
        return new ResponseEntity<Object>(createdChampionshipDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getChampionshipById(@PathVariable("id") Long id) throws ServiceException {
        Championship foundChampionship = championshipService.findChampionshipById(id);
        ChampionshipDTO foundChampionshipDTO = championshipDTOMapper.apply(foundChampionship);
        return new ResponseEntity<Object>(foundChampionshipDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> getChampionshipByName(@PathVariable("name") String name) throws ServiceException {
        Championship foundChampionship = championshipService.findChampionshipByName(name);
        ChampionshipDTO foundChampionshipDTO = championshipDTOMapper.apply(foundChampionship);
        return new ResponseEntity<Object>(foundChampionshipDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllChampionships(Pageable pageRequest) throws ServiceException{
        Page<ChampionshipDTO> championshipList = championshipService.findAllChampionships(pageRequest).map(championshipDTOMapper);
        return new ResponseEntity<Object>(championshipList, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateChampionship(@PathVariable("id") Long id, @RequestBody ChampionshipDTO championshipDTO) throws ServiceException{
        Championship championship = championshipMapper.apply(championshipDTO);
        championship.setId(id);
        Championship updatedChampionship = championshipService.updateChampionship(championship);
        ChampionshipDTO updatedChampionshipDTO = championshipDTOMapper.apply(updatedChampionship);
        return new ResponseEntity<Object>(updatedChampionshipDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteChampionship(@PathVariable("id") Long id) throws ServiceException{
        championshipService.deleteChampionship(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Championship has been deleted successfully.");
        return new ResponseEntity<Object>(messageResponse, HttpStatus.OK);
    }

}
