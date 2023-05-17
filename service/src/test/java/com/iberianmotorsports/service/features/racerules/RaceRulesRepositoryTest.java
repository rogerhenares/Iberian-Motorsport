package com.iberianmotorsports.service.features.racerules;

import com.iberianmotorsports.RaceFactory;
import com.iberianmotorsports.RaceRulesFactory;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.RaceRules;
import com.iberianmotorsports.service.repository.RaceRepository;
import com.iberianmotorsports.service.repository.RaceRulesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:it.properties")
public class RaceRulesRepositoryTest {

    @Autowired
    RaceRulesRepository raceRulesRepository;

    @Autowired
    RaceRepository raceRepository;

    RaceRules raceRulesDummy = RaceRulesFactory.raceRules();

    @BeforeEach
    public void setupDatabase() {
        Race raceDummy = RaceFactory.race();
        raceDummy.setRaceRules(raceRulesDummy);
        raceRulesDummy.setRace(raceDummy);
        raceRulesRepository.save(raceRulesDummy);
    }

    @Test
    public void testingTesting() {

    }

}
