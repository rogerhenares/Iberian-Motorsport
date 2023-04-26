package com.iberianmotorsports.service.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class SteamLoginConfiguration implements AuthenticationEntryPoint {
    private static final String HOST = "http://localhost:8080";
    private static final String BASE_LOGIN_URL = "https://steamcommunity.com/openid/login?" +
            "&openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select" +
            "&openid.identity=http://specs.openid.net/auth/2.0/identifier_select" +
            "&openid.mode=checkid_setup" +
            "&openid.ns=http://specs.openid.net/auth/2.0" +
            "&openid.realm=" + HOST +
            "&openid.return_to=";

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        String requestURL = HOST + request.getRequestURI();
        String urlRedirect = BASE_LOGIN_URL + requestURL;
        response.sendRedirect(urlRedirect);
    }
}
