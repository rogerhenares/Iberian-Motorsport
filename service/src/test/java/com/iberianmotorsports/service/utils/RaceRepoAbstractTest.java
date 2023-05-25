package com.iberianmotorsports.service.utils;

import com.iberianmotorsports.RaceFactory;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.repository.ChampionshipRepository;
import com.iberianmotorsports.service.repository.RaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:it.properties")
public class RaceRepoAbstractTest extends AbstractRepositoryIT<Race> {


    public RaceRepoAbstractTest(
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

    @Override
    protected JpaRepository<Race, Long> getRepository() {
        return raceRepository;
    }

    @Test
    void save() {
        Race raceDummy = RaceFactory.race();
        raceDummy.setChampionship(championship);
        save(raceDummy);
    }

    @Test
    void findById(){
        findById(race.getId());
    }

    @Test
    void findAllRaces() {
        findAll();
    }

    @Test
    void deleteRace() {
        delete(race.getId());
    }

}


