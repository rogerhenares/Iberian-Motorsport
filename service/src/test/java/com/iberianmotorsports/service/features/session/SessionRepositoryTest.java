package com.iberianmotorsports.service.features.session;

import com.iberianmotorsports.SessionFactory;
import com.iberianmotorsports.service.model.Session;
import com.iberianmotorsports.service.repository.ChampionshipRepository;
import com.iberianmotorsports.service.repository.RaceRepository;
import com.iberianmotorsports.service.repository.SessionRepository;
import com.iberianmotorsports.service.utils.AbstractRepositoryIT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:it.properties")
public class SessionRepositoryTest extends AbstractRepositoryIT<Session> {

    public SessionRepositoryTest(@Autowired ChampionshipRepository championshipRepository,
                                 @Autowired SessionRepository sessionRepository,
                                 @Autowired RaceRepository raceRepository) {
        super(championshipRepository, sessionRepository, null, raceRepository);
    }

    @Override
    protected JpaRepository<Session, Long> getRepository() {
        return sessionRepository;
    }

    @BeforeEach
    public void setupDataBase() {
        createSession();
    }

    @Test
    void save() {
        save(session);
    }

    @Test
    void saveWithNullValue() {
        Session sessionDummy = SessionFactory.session();
        sessionDummy.setRace(null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () ->save(sessionDummy));
    }

    @Test
    void fetchById() {
        findById(session.getId());
    }

    @Test
    void fetchAllSessions() {
        findAll();
    }

    @Test
    void delete() {
        delete(session.getId());
    }

}
