package com.iberianmotorsports.service.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<String> steamLogin() {
        String clientId = "6614BF6FC7820DF1DD2C875DB66C5D82"; // Replace with your actual client ID
        String redirectUri = "http://localhost:8080/openid/id"; // Replace with your actual redirect URI

        String authorizationUri = "https://steamcommunity.com/openid/login" +
                "?openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select" +
                "&openid.identity=http://specs.openid.net/auth/2.0/identifier_select" +
                "&openid.mode=checkid_setup" +
                "&openid.ns=http://specs.openid.net/auth/2.0" +
                "&openid.realm=" + redirectUri +
                "&openid.return_to=" + redirectUri +
                "&openid.ns.sreg=http://openid.net/extensions/sreg/1.1" +
                "&openid.sreg.optional=nickname,email,fullname,dob,gender,postcode,country";

        String loginUrl = authorizationUri +
                "&openid.ns.ax=http://openid.net/srv/ax/1.0" +
                "&openid.ax.mode=fetch_request" +
                "&openid.ax.type.email=http://axschema.org/contact/email" +
                "&openid.ax.type.fullname=http://axschema.org/namePerson" +
                "&openid.ax.type.firstname=http://axschema.org/namePerson/first" +
                "&openid.ax.type.lastname=http://axschema.org/namePerson/last" +
                "&openid.ax.required=email,fullname";

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

    @GetMapping("/openid/id")
    public String steamLoginCallback(@RequestParam("openid.identity") String identity,
                                     @RequestParam("openid.sig") String signature,
                                     @RequestParam("openid.signed") String signed,
                                     @RequestParam("openid.ns") String ns,
                                     @RequestParam("openid.mode") String mode,
                                     @RequestParam("openid.assoc_handle") String assocHandle,
                                     @RequestParam("openid.session_handle") String sessionHandle,
                                     @RequestParam("openid.return_to") String returnTo,
                                     @RequestParam("openid.response_nonce") String responseNonce,
                                     @RequestParam("openid.op_endpoint") String opEndpoint,
                                     @RequestParam("openid.ax.type.firstname") String firstNameType,
                                     @RequestParam("openid.ax.type.lastname") String lastNameType,
                                     @RequestParam("openid.ax.type.email") String emailType,
                                     @RequestParam("openid.ax.value.firstname") String firstName,
                                     @RequestParam("openid.ax.value.lastname") String lastName,
                                     @RequestParam("openid.ax.value.email") String email,
                                     HttpServletRequest request) {

        // Extract the token from the returnTo URL
        String token = request.getParameter("openid.assoc_handle");

        // Print the token to the console
        System.out.println("Token: " + token);

        return "redirect:/";
    }

}

