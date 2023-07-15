package com.iberianmotorsports.service.controller;

import com.google.protobuf.ServiceException;
import com.iberianmotorsports.service.controller.DTO.GridDTO;
import com.iberianmotorsports.service.controller.DTO.Mappers.GridDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.GridMapper;
import com.iberianmotorsports.service.controller.DTO.MessageResponse;
import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.service.GridService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/grid")
@RequiredArgsConstructor
@RestController
public class GridController {

    @Autowired
    private GridService gridService;

    @Autowired
    private GridDTOMapper gridDTOMapper;

    @Autowired
    private GridMapper gridMapper;


    @GetMapping(value=("{id}"))
    public ResponseEntity<?> getGridForChampionship(@PathVariable("id") Long championshipId) throws ServiceException {
        List<Grid> gridList = gridService.getGridForChampionship(championshipId);
        List<GridDTO> gridDTOList = gridList.stream().map(gridDTOMapper).toList();
        return new ResponseEntity<Object>(gridDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createNewGrid(@RequestBody GridDTO gridDTO) {
        GridDTO createdGridDTO = gridService.createGridEntry(gridDTO);
        return new ResponseEntity<Object>(createdGridDTO, HttpStatus.CREATED);
    }

    // TODO should we validate the Car number when we add the Driver to the grid or after?

    @PutMapping("/add/{id}")
    public ResponseEntity<?> addDriver(@PathVariable("id") Long gridId, Long steamId) {
        gridService.addDriver(gridId, steamId);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Driver added successfully");
        return new ResponseEntity<Object>(messageResponse, HttpStatus.OK);
    }

    @DeleteMapping(value="/remove/{id}")
    public ResponseEntity<?> removeDriver(@PathVariable("id") Long gridId, @RequestParam("steamId") Long steamId) {
        gridService.removeDriver(gridId, steamId);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Driver removed successfully");
        return new ResponseEntity<Object>(messageResponse, HttpStatus.OK);
    }
}