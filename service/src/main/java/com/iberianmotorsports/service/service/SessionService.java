package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.Session;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface SessionService {

    Session saveSession(Session session);

    Session findSessionById(Long id);

    Session findSessionByName(String name);

    Page<Session> findAllSessions();

    Session updateSession(Session Session);

    void deleteSession(Long id);

    Boolean isAlreadyInDatabase(Long id);

}
