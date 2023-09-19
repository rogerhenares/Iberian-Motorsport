package com.iberianmotorsports.service.controller;

import com.google.protobuf.ServiceException;
import com.iberianmotorsports.service.controller.DTO.GridDTO;
import com.iberianmotorsports.service.controller.DTO.Mappers.GridDTOMapper;
import com.iberianmotorsports.service.controller.DTO.MessageResponse;
import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.service.GridService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
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


    @GetMapping(value=("/public/{id}"))
    public ResponseEntity<?> getGridForChampionship(@PathVariable("id") Long championshipId) throws ServiceException {
        List<Grid> gridList = gridService.getGridForChampionship(championshipId);
        List<GridDTO> gridDTOList = gridList.stream().map(gridDTOMapper).toList();
        return new ResponseEntity<Object>(gridDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createNewGrid(@RequestBody GridDTO gridDTO) {
        Grid gridCreated = gridService.createGridEntry(gridDTO);
        GridDTO createdGridDTO = gridDTOMapper.apply(gridCreated);
        return new ResponseEntity<Object>(createdGridDTO, HttpStatus.CREATED);
    }

    @PutMapping(value= "/{id}")
    public ResponseEntity<?> updateGrid(@PathVariable("id") Long id, @RequestBody GridDTO gridDTO) throws ServiceException {
        Grid updatedGrid = gridService.updateGrid(gridDTO);
        GridDTO updatedGridDto = gridDTOMapper.apply(updatedGrid);
        return new ResponseEntity<Object>(updatedGridDto, HttpStatus.OK);
    }

    @PutMapping(value= "/{id}/carNumber/{carNumber}")
    public ResponseEntity<?> updateGridCarNumber(@PathVariable("id") Long id, @PathVariable("carNumber") Integer carNumber) throws ServiceException {
        Grid updatedGrid = gridService.updateGridCarNumber(id, carNumber);
        GridDTO updatedGridDTO = gridDTOMapper.apply(updatedGrid);
        return new ResponseEntity<Object>(updatedGridDTO, HttpStatus.OK);
    }

    @PutMapping(value= "/{id}/car/{carId}")
    public ResponseEntity<?> updateGridCarId(@PathVariable("id") Long id, @PathVariable("carId") Long carId) throws ServiceException {
        Grid updatedGrid = gridService.updateGridCar(id, carId);
        GridDTO updatedGridDTO = gridDTOMapper.apply(updatedGrid);
        return new ResponseEntity<Object>(updatedGridDTO, HttpStatus.OK);
    }

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

    @DeleteMapping(value="/admin/{id}")
    public ResponseEntity<?> deleteGrid(@PathVariable("id") Long gridId) {
        gridService.deleteGrid(gridId);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Entry successfully deleted");
        return new ResponseEntity<Object>(messageResponse, HttpStatus.OK);
    }

}
