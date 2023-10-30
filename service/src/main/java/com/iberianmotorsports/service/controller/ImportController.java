package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.service.ImportDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/import")
@RequiredArgsConstructor
@RestController
public class ImportController {

    @Autowired
    private ImportDataService importDataService;

    @PostMapping()
    public ResponseEntity<?> importData(@RequestBody Race race) throws Exception {
        importDataService.importData(race);
        return new ResponseEntity<Object>("OK", HttpStatus.OK);
    }

}
