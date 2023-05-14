package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.model.MessageResponse;
import com.iberianmotorsports.service.model.RaceRules;
import com.iberianmotorsports.service.service.RaceRulesService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/race-rules")
@RequiredArgsConstructor
@RestController
public class RaceRulesController {

    @Autowired
    private RaceRulesService raceRulesService;

    @PostMapping
    public ResponseEntity<?> createNewRaceRules(RaceRules raceRules) throws Exception {
        RaceRules createdRaceRules = raceRulesService.saveRaceRules(raceRules);
        return new ResponseEntity<Object>(createdRaceRules, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getRaceRulesById(@PathVariable("id") Long id) throws ServiceException {
        RaceRules raceRules = raceRulesService.findRaceRulesById(id);
        return new ResponseEntity<Object>(raceRules, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllRaceRules() throws ServiceException{
        Page<RaceRules> raceRulesList = raceRulesService.findAllRaceRules();
        return new ResponseEntity<Object>(raceRulesList, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateRaceRules(@PathVariable("id") Long id, @RequestBody RaceRules raceRules) throws ServiceException{
        raceRules.setId(id);
        RaceRules updatedRaceRules = raceRulesService.updateRaceRules(raceRules);
        return new ResponseEntity<Object>(updatedRaceRules, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteRaceRules(@PathVariable("id") Long id) throws ServiceException{
        raceRulesService.deleteRaceRules(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Race rules configuration has been deleted successfully.");
        return new ResponseEntity<Object>(messageResponse, HttpStatus.OK);
    }

}