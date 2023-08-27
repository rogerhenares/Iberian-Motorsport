package com.iberianmotorsports.service.service.implementation;

import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.model.UserAuth;
import com.iberianmotorsports.service.repository.UserAuthRepository;
import com.iberianmotorsports.service.service.AuthService;
import com.iberianmotorsports.service.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@Transactional
@Service
public class AuthServiceImpl implements AuthService {

    private static final String HOST = "http://localhost:4200/";
    private static final String REDIRECT_TO = "http://localhost:4200/login";
    private static final String BASE_LOGIN_URL = "https://steamcommunity.com/openid/login?" +
            "&openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select" +
            "&openid.identity=http://specs.openid.net/auth/2.0/identifier_select" +
            "&openid.mode=checkid_setup" +
            "&openid.ns=http://specs.openid.net/auth/2.0" +
            "&openid.realm=" + HOST +
            "&openid.return_to=" + REDIRECT_TO;
    private final static String IS_VALID = "is_valid:true";
    private final static String OPENID_MODE_ID_RES = "openid.mode=id_res";
    private final static String OPENID_MODE_CHECK_AUTHENTICATION = "openid.mode=check_authentication";
    private final static String STEAM_LOGIN_URL = "https://steamcommunity.com/openid/login?";
    private final static String OPENID_RESPONSE_NONCE_PATTERN = "openid.response_nonce=([^&]*)";

    private UserAuthRepository userAuthRepository;
    private UserService userService;
    private RestTemplate restTemplate;

    @Override
    public String newLoggingRequest(String steamResponseParametersEncoded) {
        String authDecoded = new String(Base64.getDecoder()
                .decode(steamResponseParametersEncoded), StandardCharsets.UTF_8);
        authDecoded = authDecoded.replace(OPENID_MODE_ID_RES, OPENID_MODE_CHECK_AUTHENTICATION);
        String validateTokenURL = STEAM_LOGIN_URL + authDecoded;
        ResponseEntity<String> response = restTemplate.exchange(validateTokenURL, HttpMethod.GET, null, String.class);
        String responseBody = response.getBody();

        String token = "invalid";
        assert responseBody != null;
        if (responseBody.contains(IS_VALID)) {
            Long steamId = extractSteamID(authDecoded);
            Pattern regex = Pattern.compile(OPENID_RESPONSE_NONCE_PATTERN);
            Matcher matcher = regex.matcher(authDecoded);
            if(matcher.find()){
                String responseNonce = matcher.group(1);
                UserAuth authToken = generateToken(steamId, responseNonce);
                if(!userService.isAlreadyInDatabase(steamId)) {
                    userService.saveUser(steamId);
                }
                token = authToken.getToken();
            }
        }
        return token;
    }

    @Override
    public String loggingUrl() {
        return BASE_LOGIN_URL;
    }

    @Override
    public Boolean validateLoggingToken(String token) {
        Optional<UserAuth> userAuthOptional = userAuthRepository.findUserAuthByToken(token);

        if(userAuthOptional.isEmpty())
            return Boolean.FALSE;

        long weekAgoTimestamp = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000);
        if(userAuthOptional.get().getLastLogin() < weekAgoTimestamp) {
            return Boolean.FALSE;
        }
        refreshToken(userAuthOptional.get().getSteamId());
        return Boolean.TRUE;
    }

    @Override
    public User getLoggedUser(String token) {
        UserAuth userAuth = userAuthRepository.findUserAuthByToken(token).orElseThrow(() ->
                new ServiceException(ErrorMessages.AUTH_TOKEN_NOT_FOUND.getDescription()));
        User loggedUser = userService.findUserBySteamId(userAuth.getSteamId());
        loggedUser.getRoles().size();
        return loggedUser;
    }

    private UserAuth generateToken(Long steamId, String anotherString){
        String generatedAuthToken = Base64.getEncoder()
                .encodeToString((steamId + anotherString).getBytes());
        Optional<UserAuth> userAuthOptional = userAuthRepository.findById(steamId);
        UserAuth userAuth = new UserAuth();
        if(userAuthOptional.isPresent()){
            userAuth = userAuthOptional.get();
        }
        userAuth.setSteamId(steamId);
        userAuth.setToken(generatedAuthToken);
        userAuth.setLastLogin(System.currentTimeMillis());
        userAuthRepository.save(userAuth);
        return userAuth;
    }

    private void refreshToken(Long steamId){
        UserAuth userAuth = userAuthRepository.findById(steamId).orElseThrow(() ->
            new ServiceException(ErrorMessages.AUTH_TOKEN_UNABLE_TO_REFRESH.getDescription()));
        userAuth.setLastLogin(System.currentTimeMillis());
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
