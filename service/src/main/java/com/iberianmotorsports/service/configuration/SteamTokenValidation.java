package com.iberianmotorsports.service.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SteamTokenValidation extends OncePerRequestFilter {

    private final static String IS_VALID = "is_valid:true";
    private final static String OPENID_MODE_ID_RES = "openid.mode=id_res";
    private final static String OPENID_MODE_CHECK_AUTHENTICATION = "openid.mode=check_authentication";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if(request.getHeader("Authorization") == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String authDecoded = new String(Base64.getDecoder()
                .decode(request.getHeader("Authorization")),
                StandardCharsets.UTF_8);
        authDecoded = authDecoded.replace(OPENID_MODE_ID_RES, OPENID_MODE_CHECK_AUTHENTICATION);
        String validateTokenURL = "https://steamcommunity.com/openid/login?" +
                authDecoded;
        String validationResponse = restTemplate.getForObject(validateTokenURL, String.class);

        assert validationResponse != null;
        if(validationResponse.contains(IS_VALID)){
            Authentication authentication = createAuthenticationToken(request.getParameter("openid.sig"));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private Authentication createAuthenticationToken(String token) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(authority);

        Authentication authentication = new UsernamePasswordAuthenticationToken(token, null, authorities);

        return authentication;
    }
}
