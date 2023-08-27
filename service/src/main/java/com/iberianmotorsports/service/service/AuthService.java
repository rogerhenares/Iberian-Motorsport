package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    String newLoggingRequest(String steamResponseParametersEncoded);

    String loggingUrl();

    Boolean validateLoggingToken(String token);

    User getLoggedUser(String token);

}
