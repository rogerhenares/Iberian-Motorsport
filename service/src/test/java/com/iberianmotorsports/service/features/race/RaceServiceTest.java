package com.iberianmotorsports.service.features.race;

import com.iberianmotorsports.ChampionshipFactory;
import com.iberianmotorsports.RaceFactory;
import com.iberianmotorsports.RaceRulesFactory;
import com.iberianmotorsports.SessionFactory;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.controller.DTO.Mappers.*;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.repository.ChampionshipRepository;
import com.iberianmotorsports.service.repository.RaceRepository;
import com.iberianmotorsports.service.service.ChampionshipService;
import com.iberianmotorsports.service.service.RaceRulesService;
import com.iberianmotorsports.service.service.RaceService;
import com.iberianmotorsports.service.service.SessionService;
import com.iberianmotorsports.service.service.implementation.ChampionshipServiceImpl;
import com.iberianmotorsports.service.service.implementation.RaceServiceImpl;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RaceServiceTest {

    private RaceService raceService;

    private ChampionshipService championshipService;

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private ChampionshipRepository championshipRepository;

    @Captor
    ArgumentCaptor<Race> raceCaptor;
    private RaceDTOMapper raceDTOMapper;
    private RaceMapper raceMapper;
    private ChampionshipMapper championshipMapper;
    private SessionMapper sessionMapper;
    private SessionDTOMapper sessionDTOMapper;
    private SessionService sessionService;
    private RaceRulesMapper raceRulesMapper;
    private RaceRulesDTOMapper raceRulesDTOMapper;
    private RaceRulesService raceRulesService;





    @BeforeEach
    public void init() {
        sessionMapper = new SessionMapper();
        sessionDTOMapper = new SessionDTOMapper();
        raceRulesMapper = new RaceRulesMapper();
        raceRulesDTOMapper = new RaceRulesDTOMapper();
        championshipMapper = new ChampionshipMapper();
        raceMapper = new RaceMapper(raceRulesMapper, sessionMapper, championshipRepository);
        raceDTOMapper = new RaceDTOMapper(raceRulesDTOMapper, sessionDTOMapper);
        championshipService = new ChampionshipServiceImpl(championshipRepository, championshipMapper);
        raceService = new RaceServiceImpl(championshipService, raceRepository, raceMapper, sessionService, raceRulesService);
    }

    @Nested
    class saveRace {

        @Test
        public void saveRace() {
            Race testRace = RaceFactory.race();
            testRace.setChampionship(ChampionshipFactory.championship());
            testRace.setSession(SessionFactory.session());
            testRace.setRaceRules(RaceRulesFactory.raceRules());
            givenRaceRepositorySave();
            givenChampionshipExists();

            raceService.saveRace(raceDTOMapper.apply(testRace));

            verify(raceRepository).save(raceCaptor.capture());
            assertEquals(testRace, raceCaptor.getValue());
        }

        @Test
        public void saveDuplicateRace() {
            Race testRace = RaceFactory.race();
            testRace.setChampionship(ChampionshipFactory.championship());
            testRace.setSession(SessionFactory.session());
            testRace.setRaceRules(RaceRulesFactory.raceRules());
            givenChampionshipExists();
            givenRaceAlreadyExists();

            RuntimeException exception = assertThrows(ServiceException.class,
                    ()-> raceService.saveRace(raceDTOMapper.apply(testRace)));

            verify(raceRepository, times(0)).save(any());
            Assertions.assertEquals(ErrorMessages.DUPLICATED_RACE.getDescription(), exception.getMessage());
        }

    }

    @Nested
    class updateRace {

        @Test
        public void updateRace() {
            Race testRace = RaceFactory.race();
            givenRaceRepositorySave();

            raceService.updateRace(testRace);

            verify(raceRepository).save(raceCaptor.capture());
            assertEquals(RaceFactory.race(), raceCaptor.getValue());

        }
    }


    @Nested
    class deleteRace {

        @Test
        public void deleteRace() {

            raceService.deleteRace(anyLong());

            verify(raceRepository).deleteById(anyLong());
        }
    }

    @Nested
    class findRace {

        @Test
        public void findRaceById() {
            when(raceRepository.findById(anyLong())).thenReturn(Optional.of(RaceFactory.race()));

            raceRepository.findById(anyLong());

            verify(raceRepository).findById(anyLong());
        }

        @Test
        public void findRaceByName() {
            when(raceRepository.findById(anyLong())).thenReturn(Optional.of(RaceFactory.race()));

            raceRepository.findById(anyLong());

            verify(raceRepository).findById(anyLong());
        }

        @Test
        public void findRaceInvalidId() {
            when(raceRepository.findById(anyLong())).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(ServiceException.class,
                    ()-> raceService.findRaceById(anyLong()));

            verify(raceRepository).findById(anyLong());
            Assertions.assertEquals(ErrorMessages.RACE_NOT_IN_DB.getDescription(), exception.getMessage());
        }

        @Test
        public void findRaceInvalidName() {
            when(raceRepository.findByTrack(anyString())).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(ServiceException.class, ()-> raceService.findRaceByName(anyString()));

            assertEquals(ErrorMessages.RACE_NOT_IN_DB.getDescription(), exception.getMessage());
        }
    }

    @Test
    @Disabled
    public void exportRace() throws IOException {
        Race testRace = RaceFactory.race();

        String result = raceService.exportRace(testRace);

        assertTrue(result.startsWith("Race saved to"));

        Path filePath = Paths.get(result.substring("Race saved to ".length()));
    }

    private void givenChampionshipExists() {
        when(championshipRepository.findById(anyLong())).thenReturn(Optional.of(ChampionshipFactory.championship()));
    }

    private void givenRaceRepositorySave() {
        when(raceRepository.save(any(Race.class))).then((Answer<Race>) invocation -> {
            Race race = invocation.getArgument(0);
            race.setId(1L);
            return race;
        });
    }

    private void givenRaceAlreadyExists() {
        when(raceRepository.findById(anyLong())).thenReturn(Optional.of(RaceFactory.race()));
    }

}
