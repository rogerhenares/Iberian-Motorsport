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

public class AbstractRepositoryIT {
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

    protected void createChampionship(){
        championship = ChampionshipFactory.championship();
        championship.setId(null);
        championshipRepository.save(championship);
    }

    protected void createRace(){
        createChampionship();
        race = RaceFactory.race();
        race.setId(null);
        race.setChampionship(championship);
        raceRepository.save(race);
    }

    protected void createSession(){
        createRace();
        session = SessionFactory.session();
        session.setId(null);
        session.setRace(race);
        sessionRepository.save(session);
    }

    protected void createRaceRules(){
        createRace();
        raceRules = RaceRulesFactory.raceRules();
        raceRules.setId(null);
        raceRules.setRace(race);
        raceRulesRepository.save(raceRules);
    }
}
