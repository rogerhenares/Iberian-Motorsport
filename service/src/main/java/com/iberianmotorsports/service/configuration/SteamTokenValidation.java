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
import java.util.Collections;
import java.util.List;

public class SteamTokenValidation extends OncePerRequestFilter {

    private final static String IS_VALID = "is_valid:true";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if(request.getParameter("openid.sig") == null){
            filterChain.doFilter(request, response);
            return;
        }

        String validateTokenURL = "https://steamcommunity.com/openid/login?" +
                "openid.ns=http://specs.openid.net/auth/2.0" +
                "&openid.mode=check_authentication" +
                "&openid.op_endpoint=" + request.getParameter("openid.op_endpoint") +
                "&openid.claimed_id=" + request.getParameter("openid.claimed_id") +
                "&openid.identity=" + request.getParameter("openid.identity") +
                "&openid.return_to=" + request.getParameter("openid.return_to") +
                "&openid.response_nonce=" + request.getParameter("openid.response_nonce") +
                "&openid.assoc_handle=" + request.getParameter("openid.assoc_handle") +
                "&openid.signed=" + request.getParameter("openid.signed") +
                "&openid.sig=" + request.getParameter("openid.sig");

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
