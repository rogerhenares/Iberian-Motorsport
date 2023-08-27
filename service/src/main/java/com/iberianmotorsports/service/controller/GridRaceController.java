package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.controller.DTO.GridRaceDTO;
import com.iberianmotorsports.service.controller.DTO.Mappers.GridRaceDTOMapper;
import com.iberianmotorsports.service.model.GridRace;
import com.iberianmotorsports.service.service.GridRaceService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/grid-race")
@RequiredArgsConstructor
@RestController
public class GridRaceController {

    @Autowired
    private GridRaceService gridRaceService;

    @Autowired
    private GridRaceDTOMapper gridRaceDTOMapper;


    @GetMapping(value=("/{gridId}/{raceId}"))
    public ResponseEntity<?> getGridRace(@PathVariable("gridId") Long gridId, @PathVariable("raceId") Long raceId) throws ServiceException {
        GridRace gridRace = gridRaceService.getGridRace(gridId, raceId);
        GridRaceDTO gridRaceDTO = gridRaceDTOMapper.apply(gridRace);
        return new ResponseEntity<Object>(gridRaceDTO, HttpStatus.OK);
    }

    @GetMapping(value=("/public/{raceId}"))
    public ResponseEntity<?> getGridRaceForRace(@PathVariable("raceId") Long raceId) throws ServiceException {
        List<GridRace> gridRaceList = gridRaceService.getGridRaceForRace(raceId);
        List<GridRaceDTO> gridRaceDTOList = gridRaceList.stream().map(gridRaceDTOMapper).toList();
        return new ResponseEntity<Object>(gridRaceDTOList, HttpStatus.OK);
    }

}
