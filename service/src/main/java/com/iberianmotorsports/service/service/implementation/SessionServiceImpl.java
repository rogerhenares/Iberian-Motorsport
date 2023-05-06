package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.model.Session;
import com.iberianmotorsports.service.repository.SessionRepository;
import com.iberianmotorsports.service.service.SessionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service("SessionService")
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    static final Pageable pageable = PageRequest.of(0,10);

    @Override
    public Session saveSession(Session session) {
        if (isAlreadyInDatabase(session.getId()))
            throw new ServiceException(ErrorMessages.DUPLICATED_SESSION.getDescription());
        return sessionRepository.save(session);
    }

    @Override
    public Session findSessionById(Long id) {
        Optional<Session> sessionOptional = sessionRepository.findById(id);
        if(sessionOptional.isEmpty()) throw new ServiceException(ErrorMessages.SESSION_NOT_IN_DB.getDescription());
        return sessionOptional.orElse(null);
    }

    @Override
    public Session findSessionByName(String name) {
        Optional<Session> sessionOptional = sessionRepository.findByName(name);
        if(sessionOptional.isEmpty()) throw new ServiceException(ErrorMessages.SESSION_NOT_IN_DB.getDescription());
        return sessionOptional.orElse(null);
    }

    @Override
    public Page<Session> findAllSessions() {
        return sessionRepository.findAll(pageable);
    }

    @Override
    public Session updateSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    @Override
    public Boolean isAlreadyInDatabase(Long id) {
        Optional<Session> sessionOptional = sessionRepository.findById(id);
        return sessionOptional.isPresent();
    }

    public Boolean isInDatabase(String name) {
        return null;
    }

    public String exportSession(Session session) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(session);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath() + "/session.json";
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(json);
            fileWriter.close();
            return "Session saved to " + filePath;
        } else {
            return "No directory selected";
        }
    }

}
