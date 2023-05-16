package com.iberianmotorsports.service.features.session;

import com.iberianmotorsports.SessionFactory;
import com.iberianmotorsports.service.model.Session;
import com.iberianmotorsports.service.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:it.properties")
public class SessionRepositoryTest {

    @Autowired
    SessionRepository sessionRepository;

    @BeforeEach
    public void setupDataBase() {
        Session sessionDummy = SessionFactory.session();
        sessionRepository.save(sessionDummy);
    }

    @Test
    void fetchAllSessions() {
        List<Session> sessionList = sessionRepository.findAll();
        assertFalse(sessionList.isEmpty());
    }

}
