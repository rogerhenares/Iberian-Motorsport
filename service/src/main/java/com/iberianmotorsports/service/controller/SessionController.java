package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.model.MessageResponse;
import com.iberianmotorsports.service.model.Session;
import com.iberianmotorsports.service.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/session")
@RequiredArgsConstructor
@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping
    public ResponseEntity<?> createNewSession(Session session) throws Exception {
        Session createdSession = sessionService.saveSession(session);
        return new ResponseEntity<Object>(createdSession, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getSessionById(@PathVariable("id") Long id) throws ServiceException {
        Session session = sessionService.findSessionById(id);
        return new ResponseEntity<Object>(session, HttpStatus.OK);
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<?> getSessionByName(@PathVariable("name") String name) throws ServiceException {
        Session session = sessionService.findSessionByName(name);
        return new ResponseEntity<Object>(session, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllSessions() throws ServiceException{
        Page<Session> sessionList = sessionService.findAllSessions();
        return new ResponseEntity<Object>(sessionList, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateSession(@PathVariable("id") Long id, @RequestBody Session session) throws ServiceException{
        session.setId(id);
        Session updatedSession = sessionService.updateSession(session);
        return new ResponseEntity<Object>(updatedSession, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable("id") Long id) throws ServiceException{
        if(!sessionService.isAlreadyInDatabase(id)) {
            throw new ServiceException(ErrorMessages.RACE_NOT_IN_DB.getDescription());
        }
        sessionService.deleteSession(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Session has been deleted successfully.");
        return new ResponseEntity<Object>(messageResponse, HttpStatus.OK);
    }

}
