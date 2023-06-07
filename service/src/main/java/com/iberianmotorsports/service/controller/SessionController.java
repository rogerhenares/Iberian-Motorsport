package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.controller.DTO.Mappers.SessionDTOMapper;
import com.iberianmotorsports.service.controller.DTO.SessionDTO;
import com.iberianmotorsports.service.model.MessageResponse;
import com.iberianmotorsports.service.model.Session;
import com.iberianmotorsports.service.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/session")
@RequiredArgsConstructor
@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    private SessionDTOMapper sessionDTOMapper;

    @PostMapping
    public ResponseEntity<?> createNewSession(SessionDTO sessionDTO) throws Exception {
        Session createdSession = sessionService.saveSession(sessionDTO);
        SessionDTO createdSessionDTO = sessionDTOMapper.apply(createdSession);
        return new ResponseEntity<Object>(createdSessionDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getSessionById(@PathVariable("id") Long id) throws ServiceException {
        Session session = sessionService.findSessionById(id);
        return new ResponseEntity<Object>(session, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<?> getAllSessions(Pageable pageRequest) throws ServiceException{
        Page<Session> sessionList = sessionService.findAllSessions(pageRequest);
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
        sessionService.deleteSession(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Session has been deleted successfully.");
        return new ResponseEntity<Object>(messageResponse, HttpStatus.OK);
    }

}
