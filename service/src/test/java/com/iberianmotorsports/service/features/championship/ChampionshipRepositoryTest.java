package com.iberianmotorsports.service.features.championship;

import com.iberianmotorsports.ChampionshipFactory;
import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.repository.ChampionshipRepository;
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

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:it.properties")
public class ChampionshipRepositoryTest extends AbstractRepositoryIT {

    public ChampionshipRepositoryTest(
            @Autowired
            ChampionshipRepository championshipRepository) {
        super(championshipRepository, null, null, null);
    }

    @BeforeEach
    public void setupDataBase() {
        createChampionship();
    }

    @Test
    void saveChampionshipNullValue() {
        Championship championshipDummy = ChampionshipFactory.championship();
        championshipDummy.setName(null);
        assertThrows(ConstraintViolationException.class, () -> championshipRepository.save(championshipDummy));
    }

    @Test
    void fetchChampionshipById() {
        Championship championshipDummy = championshipRepository.findAll().get(0);
        Long id = championshipDummy.getId();

        Optional<Championship> foundChampionship = championshipRepository.findById(id);
        Assertions.assertThat(foundChampionship.isPresent());
        Assertions.assertThat(foundChampionship.get()).isEqualTo(championshipDummy);
    }

    @Test
    void fetchChampionshipByName() {
        Championship championshipDummy = championshipRepository.findAll().get(0);
        String name = championshipDummy.getName();

        Optional<Championship> foundChampionship = championshipRepository.findByName(name);
        Assertions.assertThat(foundChampionship.isPresent());
        Assertions.assertThat(foundChampionship.get()).isEqualTo(championshipDummy);
    }

    @Test
    void fetchAllChampionship() {
        List<Championship> championshipList = championshipRepository.findAll();
        assertFalse(championshipList.isEmpty());
    }

    @Test
    void deleteChampionship() {
        Championship championshipDummy = championshipRepository.findAll().get(0);
        championshipRepository.delete(championshipDummy);

        assertTrue(championshipRepository.findById(championshipDummy.getId()).isEmpty());
    }

}
