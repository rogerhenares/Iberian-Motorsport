package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.Session;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface SessionService {

    Session saveSession(Session session);

    Session findSessionById(Long id);

    Page<Session> findAllSessions();

    Session updateSession(Session Session);

    void deleteSession(Long id);

    Boolean isAlreadyInDatabase(Long id);

    String exportSession(Session session) throws IOException;

}
