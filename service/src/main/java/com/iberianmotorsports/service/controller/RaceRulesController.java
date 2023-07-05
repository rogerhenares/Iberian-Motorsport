package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.controller.DTO.Mappers.RaceRulesDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceRulesMapper;
import com.iberianmotorsports.service.controller.DTO.RaceDTO;
import com.iberianmotorsports.service.controller.DTO.RaceRulesDTO;
import com.iberianmotorsports.service.model.MessageResponse;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.RaceRules;
import com.iberianmotorsports.service.service.RaceRulesService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/race-rules")
@RequiredArgsConstructor
@RestController
public class RaceRulesController {

    @Autowired
    private RaceRulesService raceRulesService;
    @Autowired
    private RaceRulesDTOMapper raceRulesDTOMapper;
    @Autowired
    private RaceRulesMapper raceRulesMapper;

    @PostMapping
    public ResponseEntity<?> createNewRaceRules(RaceRulesDTO raceRulesDTO, Race race) throws Exception {
        RaceRules createdRaceRules = raceRulesService.saveRaceRules(raceRulesDTO, race);
        RaceRulesDTO createdRaceRulesDTO = raceRulesDTOMapper.apply(createdRaceRules);
        return new ResponseEntity<Object>(createdRaceRulesDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getRaceRulesById(@PathVariable("id") Long id) throws ServiceException {
        RaceRules raceRules = raceRulesService.findRaceRulesById(id);
        RaceRulesDTO raceRulesDTO = raceRulesDTOMapper.apply(raceRules);
        return new ResponseEntity<Object>(raceRulesDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllRaceRules(Pageable pageRequest) throws ServiceException{
        Page<RaceRulesDTO> raceRulesList = raceRulesService.findAllRaceRules(pageRequest).map(raceRulesDTOMapper);
        return new ResponseEntity<Object>(raceRulesList, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateRaceRules(@PathVariable("id") Long id, @RequestBody RaceRulesDTO raceRulesDTO) throws ServiceException{
        RaceRules raceRules = raceRulesMapper.apply(raceRulesDTO);
        raceRules.setId(id);
        RaceRules updatedRaceRules = raceRulesService.updateRaceRules(raceRules);
        RaceRulesDTO updatedRaceRulesDTO = raceRulesDTOMapper.apply(updatedRaceRules);
        return new ResponseEntity<Object>(updatedRaceRulesDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteRaceRules(@PathVariable("id") Long id) throws ServiceException{
        raceRulesService.deleteRaceRules(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Race rules configuration has been deleted successfully.");
        return new ResponseEntity<Object>(messageResponse, HttpStatus.OK);
    }

}