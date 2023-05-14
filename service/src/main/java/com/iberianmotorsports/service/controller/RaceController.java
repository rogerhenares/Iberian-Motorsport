package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.model.MessageResponse;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.service.RaceService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/race")
@RequiredArgsConstructor
@RestController
public class RaceController {

    @Autowired
    private RaceService raceService;

    @PostMapping
    public ResponseEntity<?> createNewRace(Race race) throws Exception {
        Race createdRace = raceService.saveRace(race);
        return new ResponseEntity<Object>(createdRace, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getRaceById(@PathVariable("id") Long id) throws ServiceException {
        Race race = raceService.findRaceById(id);
        return new ResponseEntity<Object>(race, HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> getRaceByName(@PathVariable("name") String name) throws ServiceException {
        Race race = raceService.findRaceByName(name);
        return new ResponseEntity<Object>(race, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllRaces() throws ServiceException{
        Page<Race> raceList = raceService.findAllRaces();
        return new ResponseEntity<Object>(raceList, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateRace(@PathVariable("id") Long id, @RequestBody Race race) throws ServiceException{
        race.setId(id);
        Race updatedRace = raceService.updateRace(race);
        return new ResponseEntity<Object>(updatedRace, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteRace(@PathVariable("id") Long id) throws ServiceException{
        raceService.deleteRace(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Race has been deleted successfully.");
        return new ResponseEntity<Object>(messageResponse, HttpStatus.OK);
    }

}
