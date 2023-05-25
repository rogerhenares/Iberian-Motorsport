package com.iberianmotorsports.service.utils;

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
import com.iberianmotorsports.service.repository.RaceRulesRepository;
import com.iberianmotorsports.service.repository.SessionRepository;
import org.assertj.core.api.Assertions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public abstract class AbstractRepositoryIT<T> {
    protected ChampionshipRepository championshipRepository;
    protected SessionRepository sessionRepository;
    protected RaceRulesRepository raceRulesRepository;
    protected RaceRepository raceRepository;

    protected Championship championship;
    protected Race race;
    protected RaceRules raceRules;
    protected Session session;


    public AbstractRepositoryIT(ChampionshipRepository championshipRepository,
                                SessionRepository sessionRepository,
                                RaceRulesRepository raceRulesRepository,
                                RaceRepository raceRepository) {
        this.championshipRepository = championshipRepository;
        this.sessionRepository = sessionRepository;
        this.raceRulesRepository = raceRulesRepository;
        this.raceRepository = raceRepository;
    }

    protected void createChampionship() {
        championship = ChampionshipFactory.championship();
        championship.setId(null);
        championshipRepository.save(championship);
    }

    protected void createRace() {
        createChampionship();
        race = RaceFactory.race();
        race.setId(null);
        race.setChampionship(championship);
        raceRepository.save(race);
    }

    protected void createSession() {
        createRace();
        session = SessionFactory.session();
        session.setId(null);
        session.setRace(race);
        sessionRepository.save(session);
    }

    protected void createRaceRules() {
        createRace();
        raceRules = RaceRulesFactory.raceRules();
        raceRules.setId(null);
        raceRules.setRace(race);
        raceRulesRepository.save(raceRules);
    }


    protected abstract JpaRepository<T, Long> getRepository();

    protected void save(T entity) {
        T savedEntity = getRepository().save(entity);
        Field savedEntityId =  Arrays.stream(savedEntity.getClass().getDeclaredFields())
                .filter(field -> field.getName().equals("id")).toList().stream().findFirst().get();
        assertNotNull(savedEntityId);
    }

    protected void findById(Long id) {
        Optional<T> foundEntity = getRepository().findById(id);
        Assertions.assertThat(foundEntity.isPresent());
    }


    protected void findAll() {
        List<T> raceList = getRepository().findAll();
        assertFalse(raceList.isEmpty());
    }


    protected void delete(Long id) {
        assertDoesNotThrow(()-> getRepository().deleteById(id));
    }
}

