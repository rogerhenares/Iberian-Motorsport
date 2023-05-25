package com.iberianmotorsports.service.features.racerules;

import com.iberianmotorsports.RaceRulesFactory;
import com.iberianmotorsports.service.model.RaceRules;
import com.iberianmotorsports.service.repository.ChampionshipRepository;
import com.iberianmotorsports.service.repository.RaceRepository;
import com.iberianmotorsports.service.repository.RaceRulesRepository;
import com.iberianmotorsports.service.utils.AbstractRepositoryIT;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:it.properties")
public class RaceRulesRepositoryTest extends AbstractRepositoryIT<RaceRules> {

    public RaceRulesRepositoryTest(@Autowired RaceRulesRepository raceRulesRepository, @Autowired RaceRepository raceRepository, @Autowired ChampionshipRepository championshipRepository) {
        super(championshipRepository, null, raceRulesRepository, raceRepository);
    }

    @BeforeEach
    public void setupDatabase() {
        createRaceRules();
    }

    @Override
    protected JpaRepository<RaceRules, Long> getRepository() {
        return raceRulesRepository;
    }

    @Test
    void save() {
        save(raceRules);
    }

    @Test
    public void saveRaceRulesWithNullValue() {
        RaceRules raceRulesDummy = RaceRulesFactory.raceRules();
        raceRulesDummy.setQualifyStandingType(null);
        assertThrows(ConstraintViolationException.class, () -> save(raceRulesDummy));
    }

    @Test
    void findById() {
        findById(raceRules.getId());
    }

    @Test
    void fetchAll() {
        findAll();
    }

    @Test
    void deleteRaceRules() {
        delete(raceRules.getId());
    }

}
