package com.iberianmotorsports.service.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
@RequestMapping("")
public class AuthController {

    private final String API_BASE_URL = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002";
    private final String API_KEY = "BD892C8DF1AB5C4F263E07FD239AE649";
    private RestTemplate restTemplate = new RestTemplate();
    @GetMapping("public/login")
    public ResponseEntity<String> steamLogin() {
        String loginUrl = "test";

        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>Login</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Login</h1>\n" +
                "<a href=\"" + loginUrl + "\"><img src=\"https://steamcommunity-a.akamaihd.net/public/images/signinthroughsteam/sits_02.png\" alt=\"Login to Steam\"></a>\n" +
                "</body>\n" +
                "</html>";

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(html, headers, HttpStatus.OK);
    }

    @GetMapping("get")
    public ResponseEntity<String> steamLoginCallback(@RequestParam Map<String,String> allParams) {
        String steamUserId = allParams.get("openid.identity")
                .substring(allParams.get("openid.identity").lastIndexOf("/")+1);
        String url = API_BASE_URL + "/?key=" + API_KEY + "&steamids=" + steamUserId;
        String response = restTemplate.getForObject(url, String.class);
        return new ResponseEntity<>(response + allParams, new HttpHeaders(), HttpStatus.OK);
    }

}

