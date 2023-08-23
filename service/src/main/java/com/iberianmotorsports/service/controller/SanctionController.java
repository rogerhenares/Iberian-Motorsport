package com.iberianmotorsports.service.controller;

import com.google.protobuf.ServiceException;
import com.iberianmotorsports.service.controller.DTO.Mappers.SanctionDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.SanctionMapper;
import com.iberianmotorsports.service.controller.DTO.MessageResponse;
import com.iberianmotorsports.service.controller.DTO.SanctionDTO;
import com.iberianmotorsports.service.model.Sanction;
import com.iberianmotorsports.service.service.SanctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/sanction")
@RequiredArgsConstructor
@RestController
public class SanctionController {

    private SanctionService sanctionService;

    private SanctionDTOMapper sanctionDTOMapper;

    private SanctionMapper sanctionMapper;

    @PostMapping
    public ResponseEntity<SanctionDTO> createSanction(@RequestBody SanctionDTO sanctionDTO) {
        Sanction savedSanction = sanctionService.createSanction(sanctionDTO);
        return new ResponseEntity<>(sanctionDTOMapper.apply(savedSanction),HttpStatus.OK);
    }

    @GetMapping(value="/{raceId}")
    public ResponseEntity<List<SanctionDTO>> getSanctionByRaceId(@PathVariable("raceId")Long raceId) throws ServiceException {
        List<Sanction> sanctionList = sanctionService.getSanctionsByRace(raceId);
        List<SanctionDTO> sanctionDTOList = sanctionList.stream().map(sanctionDTOMapper).toList();
        return new ResponseEntity<>(sanctionDTOList, HttpStatus.OK);
    }


   @DeleteMapping(value="/delete/{id}")
   public ResponseEntity<MessageResponse> deleteSanction(@PathVariable("id")Long sanctionId) throws ServiceException {
        sanctionService.deleteSanction(sanctionId);
       MessageResponse messageResponse = new MessageResponse();
       messageResponse.setMessage("Sanction deleted successfully");
       return new ResponseEntity<>(messageResponse, HttpStatus.OK);
   }

}
