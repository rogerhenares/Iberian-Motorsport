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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:it.properties")
public class ChampionshipRepositoryTest extends AbstractRepositoryIT<Championship> {

    public ChampionshipRepositoryTest(
            @Autowired
            ChampionshipRepository championshipRepository) {
        super(championshipRepository, null, null, null);
    }

    @BeforeEach
    public void setupDataBase() {
        createChampionship();
    }

    @Override
    protected JpaRepository<Championship, Long> getRepository() {
        return championshipRepository;
    }

    @Test
    void save() {
        save(championship);
    }

    @Test
    void saveChampionshipNullValue() {
        Championship championshipDummy = ChampionshipFactory.championship();
        championshipDummy.setName(null);
        assertThrows(ConstraintViolationException.class, () ->save(championshipDummy));
    }

    @Test
    void fetchChampionshipById() {
        findById(championship.getId());
    }

    @Test
    void fetchChampionshipByName() {
        String name = championship.getName();
        Optional<Championship> foundChampionship = championshipRepository.findByName(name);
        assertTrue(foundChampionship.isPresent());
        Assertions.assertThat(foundChampionship.get()).isEqualTo(championship);
    }

    @Test
    void fetchAllChampionship() {
        findAll();
    }

    @Test
    void deleteChampionship() {
        delete(championship.getId());
    }


}
