package com.iberianmotorsports.service.controller;

import com.google.protobuf.ServiceException;
import com.iberianmotorsports.service.controller.DTO.AuthDTO;
import com.iberianmotorsports.service.controller.DTO.MessageResponse;
import com.iberianmotorsports.service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/url")
    public ResponseEntity<MessageResponse> getLoggingUrl() {
        String url = authService.loggingUrl();
        MessageResponse response = new MessageResponse();
        response.setMessage(url);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/logout/{steamId}")
    public ResponseEntity<?> logout(@PathVariable("steamId") Long steamId) throws ServiceException {
        authService.logout(steamId);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("User has been logged out successfully.");
        return new ResponseEntity<Object>(messageResponse, HttpStatus.OK);
    }
}

