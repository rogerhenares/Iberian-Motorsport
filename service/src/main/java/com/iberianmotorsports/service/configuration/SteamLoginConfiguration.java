package com.iberianmotorsports.service.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class SteamLoginConfiguration implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        //response.setHeader("location", BASE_LOGIN_URL);
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
