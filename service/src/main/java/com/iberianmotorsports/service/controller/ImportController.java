package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.service.ImportDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/import")
@RequiredArgsConstructor
@RestController
public class ImportController {

    private final ImportDataService importDataService;

    @PostMapping()
    public ResponseEntity<?> importData(@RequestBody Race race) throws Exception {
        importDataService.importData(race);
        return new ResponseEntity<Object>("OK", HttpStatus.OK);
    }

}
