package com.iberianmotorsports.service.features.race;

import com.iberianmotorsports.RaceFactory;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.repository.ChampionshipRepository;
import com.iberianmotorsports.service.repository.RaceRepository;
import com.iberianmotorsports.service.utils.AbstractRepositoryIT;
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
public class RaceRepositoryTest extends AbstractRepositoryIT {

    public RaceRepositoryTest(
            @Autowired
            ChampionshipRepository championshipRepository,
            @Autowired
            RaceRepository raceRepository) {
        super(championshipRepository, null, null, raceRepository);
    }


    @BeforeEach
    public void setupDataBase() {
        createRace();
    }


    @Test
    void saveRaceWithNullValue() {
        Race raceDummy = RaceFactory.race();
        raceDummy.setTrack(null);
        assertThrows(ConstraintViolationException.class, () -> raceRepository.save(raceDummy));
    }

    @Test
    void fetchById() {
        Long id = race.getId();
        Optional<Race> foundRace = raceRepository.findById(id);

        assertTrue(foundRace.isPresent());
        Assertions.assertThat(foundRace.get()).isEqualTo(race);
    }

    @Test
    void fetchByTrack() {
        String name = race.getTrack();

        Optional<Race> foundRace = raceRepository.findRaceByTrack(name);
        assertTrue(foundRace.isPresent());
        Assertions.assertThat(foundRace.get()).isEqualToIgnoringGivenFields(race,  "id", "championship");
    }

    @Test
    void fetchAll() {
        List<Race> raceList = raceRepository.findAll();
        assertFalse(raceList.isEmpty());
    }


    @Test
    void deleteRace() {
        raceRepository.delete(race);
        assertTrue(raceRepository.findById(race.getId()).isEmpty());
    }
}
