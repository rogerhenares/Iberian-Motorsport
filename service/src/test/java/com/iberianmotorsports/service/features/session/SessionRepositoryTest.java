package com.iberianmotorsports.service.features.session;

import com.iberianmotorsports.ChampionshipFactory;
import com.iberianmotorsports.RaceFactory;
import com.iberianmotorsports.SessionFactory;
import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.Session;
import com.iberianmotorsports.service.repository.ChampionshipRepository;
import com.iberianmotorsports.service.repository.RaceRepository;
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

    @Autowired
    RaceRepository raceRepository;

    @Autowired
    ChampionshipRepository championshipRepository;

    @BeforeEach
    public void setupDataBase() {
        Championship championship = ChampionshipFactory.championship();
        championship.setId(null);
        championshipRepository.save(championship);
        Race raceDummy = RaceFactory.race();
        raceDummy.setId(null);
        raceDummy.setChampionship(championship);
        raceRepository.save(raceDummy);
        Session sessionDummy = SessionFactory.session();
        sessionDummy.setId(null);
        sessionDummy.setRace(raceDummy);
        sessionRepository.save(sessionDummy);
    }

    @Test
    void fetchAllSessions() {
        List<Session> sessionList = sessionRepository.findAll();
        assertFalse(sessionList.isEmpty());
    }

}
