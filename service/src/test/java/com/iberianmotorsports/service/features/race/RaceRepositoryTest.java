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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:it.properties")
public class RaceRepositoryTest {

    @Autowired
    RaceRepository raceRepository;

    @Autowired
    ChampionshipRepository championshipRepository;

    Race raceDummy = RaceFactory.race();


    @BeforeEach
    public void setupDataBase() {
        RaceRules raceRulesDummy = RaceRulesFactory.raceRules();
        Session sessionDummy = SessionFactory.session();
        sessionDummy.setRace(raceDummy);
        raceRulesDummy.setRace(raceDummy);
        Championship championshipDummy = championshipRepository.save(ChampionshipFactory.championship());
        raceDummy.setChampionship(championshipDummy);
        raceRepository.save(raceDummy);
    }


    @Test
    void saveRaceWithNullValue() {
        Race raceDummy = RaceFactory.race();
        raceDummy.setTrack(null);
        assertThrows(ConstraintViolationException.class, () -> raceRepository.save(raceDummy));
    }

    @Test
    void fetchById() {
        List<Race> raceList = raceRepository.findAll();
        Long id = raceDummy.getId();
        Optional<Race> foundRace = raceRepository.findById(id);

        assertNotNull(foundRace);
        Assertions.assertThat(foundRace).isEqualTo(raceDummy);
    }

    @Test
    void fetchByTrack() {
        String name = raceDummy.getTrack();

        Optional<Race> foundRace = raceRepository.findRaceByTrack(name);
        Assertions.assertThat(foundRace.isPresent());
        Assertions.assertThat(foundRace.get()).isEqualToIgnoringGivenFields(raceDummy,  "id", "championship");
    }

    @Test
    void fetchAll() {
        List<Race> raceList = raceRepository.findAll();
        assertFalse(raceList.isEmpty());
    }


    @Test
    void deleteRace() {
        raceRepository.delete(raceDummy);
        assertTrue(raceRepository.findById(raceDummy.getId()).isEmpty());
    }
}
