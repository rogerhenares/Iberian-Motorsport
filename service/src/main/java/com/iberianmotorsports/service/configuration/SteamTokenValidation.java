package com.iberianmotorsports.service.configuration;

import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class SteamTokenValidation extends OncePerRequestFilter {

    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if(request.getHeader("Authorization") == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if(authService.validateLoggingToken(request.getHeader("Authorization"))){
            Authentication authentication = createAuthenticationToken(request.getHeader("Authorization"));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private Authentication createAuthenticationToken(String token) {
        User loggedUser = authService.getLoggedUser(token);

        List<SimpleGrantedAuthority> authorities = loggedUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .toList();

        return new UsernamePasswordAuthenticationToken(loggedUser.getSteamId(), token, authorities);
    }

}
