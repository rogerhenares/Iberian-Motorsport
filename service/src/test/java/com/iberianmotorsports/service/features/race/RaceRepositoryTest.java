package com.iberianmotorsports.service.features.race;

import com.iberianmotorsports.ChampionshipFactory;
import com.iberianmotorsports.RaceFactory;
import com.iberianmotorsports.RaceRulesFactory;
import com.iberianmotorsports.SessionFactory;
import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.RaceRules;
import com.iberianmotorsports.service.model.Session;
import com.iberianmotorsports.service.repository.ChampionshipRepository;
import com.iberianmotorsports.service.repository.RaceRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:it.properties")
public class RaceRepositoryTest {

    @Autowired
    RaceRepository raceRepository;

    @Autowired
    ChampionshipRepository championshipRepository;

    @BeforeEach
    public void setupDataBase() {
        Race raceDummy = RaceFactory.race();
        RaceRules raceRulesDummy = RaceRulesFactory.raceRules();
        Session sessionDummy = SessionFactory.session();
        sessionDummy.setRace(raceDummy);
        raceRulesDummy.setRace(raceDummy);
        Championship championshipDummy = championshipRepository.save(ChampionshipFactory.championship());
        raceDummy.setChampionship(championshipDummy);
        raceRepository.save(raceDummy);
    }

    @Test
    void testingTesting() {}


    @Test
    void saveRaceWithNullValue() {
        Race raceDummy = RaceFactory.race();
        raceDummy.setTrack(null);
        assertThrows(ConstraintViolationException.class, () -> raceRepository.save(raceDummy));
    }

}
