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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:it.properties")
public class RaceRepositoryTest extends AbstractRepositoryIT<Race>{


    public RaceRepositoryTest(
            @Autowired
            ChampionshipRepository championshipRepository,
            @Autowired
            RaceRepository raceRepository) {
        super(championshipRepository, null, null, raceRepository);
    }

    @Override
    protected JpaRepository<Race, Long> getRepository() {
        return raceRepository;
    }


    @BeforeEach
    public void setupDataBase() {
        createRace();
    }

    @Test
    void save() {
        save(race);
    }

    @Test
    void saveRaceWithNullValue() {
        Race raceDummy = RaceFactory.race();
        raceDummy.setChampionship(championship);
        raceDummy.setTrack(null);
        assertThrows(ConstraintViolationException.class, () -> save(raceDummy));
    }

    @Test
    void fetchById() {
        findById(race.getId());
    }

    @Test
    void fetchByTrack() {
        String name = race.getTrack();
        Optional<Race> foundRace = raceRepository.findByTrack(name);
        assertTrue(foundRace.isPresent());
        Assertions.assertThat(foundRace.get()).isEqualToIgnoringGivenFields(race,  "id", "championship");
    }

    @Test
    void fetchAll() {
        findAll();
    }


    @Test
    void deleteRace() {
        delete(race.getId());
    }


}

