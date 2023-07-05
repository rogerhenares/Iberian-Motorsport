package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.controller.DTO.Mappers.RaceDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceMapper;
import com.iberianmotorsports.service.controller.DTO.RaceDTO;
import com.iberianmotorsports.service.controller.DTO.MessageResponse;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.service.RaceService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/race")
@RequiredArgsConstructor
@RestController
public class RaceController {

    @Autowired
    private RaceService raceService;

    @Autowired
    private RaceDTOMapper raceDTOMapper;

    @Autowired
    private RaceMapper raceMapper;

    @PostMapping
    public ResponseEntity<?> createNewRace(@RequestBody RaceDTO raceDTO){
        Race createdRace = raceService.saveRace(raceDTO);
        RaceDTO createdRaceDTO = raceDTOMapper.apply(createdRace);
        return new ResponseEntity<Object>(createdRaceDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getRaceById(@PathVariable("id") Long id) throws ServiceException {
        Race race = raceService.findRaceById(id);
        RaceDTO raceDTO = raceDTOMapper.apply(race);
        return new ResponseEntity<Object>(raceDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> getRaceByName(@PathVariable("name") String name) throws ServiceException {
        Race race = raceService.findRaceByName(name);
        RaceDTO raceDTO = raceDTOMapper.apply(race);
        return new ResponseEntity<Object>(raceDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllRaces(Pageable pageRequest) throws ServiceException{
        Page<RaceDTO> raceList = raceService.findAllRaces(pageRequest).map(raceDTOMapper);
        return new ResponseEntity<Object>(raceList, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateRace(@PathVariable("id") Long id, @RequestBody RaceDTO raceDTO) throws ServiceException{
        Race race = raceMapper.apply(raceDTO);
        race.setId(id);
        Race updatedRace = raceService.updateRace(race);
        RaceDTO updatedRaceDTO = raceDTOMapper.apply(updatedRace);
        return new ResponseEntity<Object>(updatedRaceDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteRace(@PathVariable("id") Long id) throws ServiceException{
        raceService.deleteRace(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Race has been deleted successfully.");
        return new ResponseEntity<Object>(messageResponse, HttpStatus.OK);
    }

}
