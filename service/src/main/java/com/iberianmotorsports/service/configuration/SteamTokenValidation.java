package com.iberianmotorsports.service.configuration;

import com.iberianmotorsports.service.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SteamTokenValidation extends OncePerRequestFilter {

    private final static String IS_VALID = "is_valid:true";
    private final static String OPENID_MODE_ID_RES = "openid.mode=id_res";
    private final static String OPENID_MODE_CHECK_AUTHENTICATION = "openid.mode=check_authentication";
    private RestTemplate restTemplate = new RestTemplate();

    private final UserService userService;

    public SteamTokenValidation(UserService userService) {
        this.userService = userService;
    }
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
        String validationResponse = "";
        int tryCounter = 0;
        while (tryCounter < 10){
            tryCounter = tryCounter+1;
            validationResponse = restTemplate.getForObject(validateTokenURL, String.class);
        }

        assert validationResponse != null;
        if(validationResponse.contains(IS_VALID)){
            Long steamId = extractSteamID(authDecoded);
            if(!userService.isAlreadyInDatabase(steamId)){
                userService.saveUser(steamId);
            }
            Authentication authentication = createAuthenticationToken(request.getHeader("Authorization"));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private Authentication createAuthenticationToken(String token) {
        //TODO implement roleService
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(authority);

        Authentication authentication = new UsernamePasswordAuthenticationToken(token, null, authorities);

        return authentication;
    }

    public Long extractSteamID(String input) {
        Pattern pattern = Pattern.compile("openid\\.identity=https://steamcommunity\\.com/openid/id/(\\d+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return Long.valueOf(matcher.group(1));
        }

        return null;
    }
}
