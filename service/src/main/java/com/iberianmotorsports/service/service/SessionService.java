package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.controller.DTO.SessionDTO;
import com.iberianmotorsports.service.model.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface SessionService {

    Session saveSession(SessionDTO sessionDTO);

    Session findSessionById(Long id);

    Page<Session> findAllSessions(Pageable pageable);

    Session updateSession(Session Session);

    void deleteSession(Long id);

    Boolean isAlreadyInDatabase(Long id);

    String exportSession(Session session) throws IOException;

}
