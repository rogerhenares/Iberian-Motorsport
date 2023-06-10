package com.iberianmotorsports.service.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class SteamLoginConfiguration implements AuthenticationEntryPoint {
    private static final String HOST = "http://localhost:4200/";
    private static final String REDIRECT_TO = "http://localhost:4200/login";
    private static final String BASE_LOGIN_URL = "https://steamcommunity.com/openid/login?" +
            "&openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select" +
            "&openid.identity=http://specs.openid.net/auth/2.0/identifier_select" +
            "&openid.mode=checkid_setup" +
            "&openid.ns=http://specs.openid.net/auth/2.0" +
            "&openid.realm=" + HOST +
            "&openid.return_to=" + REDIRECT_TO;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        response.setHeader("location", BASE_LOGIN_URL);
        response.sendError(HttpServletResponse.SC_FORBIDDEN, BASE_LOGIN_URL);
    }
}
