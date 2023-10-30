package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.controller.DTO.Mappers.RaceMapper;
import com.iberianmotorsports.service.controller.DTO.RaceDTO;
import com.iberianmotorsports.service.service.ExportDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/export")
@RequiredArgsConstructor
@RestController
public class ExportController {

    private final ExportDataService exportDataService;
    private final RaceMapper raceMapper;

    @PostMapping()
    public ResponseEntity<?> exportData(@RequestBody RaceDTO raceDTO) throws Exception {
        exportDataService.exportData(raceMapper.apply(raceDTO));
        return new ResponseEntity<Object>("OK", HttpStatus.OK);
    }

}
