package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.controller.DTO.AuthDTO;
import com.iberianmotorsports.service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/public/login")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public ResponseEntity<?> getLoggingToken(@RequestParam String steamEncoded)  {
        String token = authService.newLoggingRequest(steamEncoded);
        return ResponseEntity.ok(new AuthDTO(token));
    }

}

