package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.service.ExportDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/export")
@RequiredArgsConstructor
@RestController
public class ExportController {

    @Autowired
    private ExportDataService exportDataService;

    @PostMapping()
    public ResponseEntity<?> exportData(@RequestBody Race race) throws Exception {
        exportDataService.exportData(race);
        return new ResponseEntity<Object>("OK", HttpStatus.OK);
    }

}
