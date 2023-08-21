package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.model.ChampionshipCategory;
import com.iberianmotorsports.service.service.ChampionshipCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/category")
@RequiredArgsConstructor
@RestController
public class ChampionshipCategoryController {

    @Autowired
    private ChampionshipCategoryService championshipCategoryService;

    @GetMapping(value= "/{id}")
    public ResponseEntity<?> findCategoryByChampionshipId(@PathVariable("id") Long id) {
        ChampionshipCategory category = championshipCategoryService.findCategoryByChampionshipId(id);
        return new ResponseEntity<Object>(category, HttpStatus.OK);
    }

}
