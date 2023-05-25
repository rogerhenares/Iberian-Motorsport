package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.MessageResponse;
import com.iberianmotorsports.service.service.ChampionshipService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/championship")
@RequiredArgsConstructor
@RestController
public class ChampionshipController {

    @Autowired
    private ChampionshipService championshipService;

    @PostMapping
    public ResponseEntity<?> createNewChampionship(Championship championship) throws Exception {
        Championship createdChampionship = championshipService.saveChampionship(championship);
        return new ResponseEntity<Object>(createdChampionship, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getChampionshipById(@PathVariable("id") Long id) throws ServiceException {
        Championship championship = championshipService.findChampionshipById(id);
        return new ResponseEntity<Object>(championship, HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> getChampionshipByName(@PathVariable("name") String name) throws ServiceException {
        Championship championship = championshipService.findChampionshipByName(name);
        return new ResponseEntity<Object>(championship, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllChampionships() throws ServiceException{
        Page<Championship> championshipList = championshipService.findAllChampionships();
        return new ResponseEntity<Object>(championshipList, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateChampionship(@PathVariable("id") Long id, @RequestBody Championship championship) throws ServiceException{
        championship.setId(id);
        Championship updatedChampionship = championshipService.updateChampionship(championship);
        return new ResponseEntity<Object>(updatedChampionship, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteChampionship(@PathVariable("id") Long id) throws ServiceException{
        championshipService.deleteChampionship(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Championship has been deleted successfully.");
        return new ResponseEntity<Object>(messageResponse, HttpStatus.OK);
    }

}
